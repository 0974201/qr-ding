package com.example.qrzooi;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;



//todo: alle typos fixen, comments toevoegen en dit opsplitsen bc what the shit is this even

public class CameraDing extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String FRAGMENT_DIALOG = "dialog";
    private static final String TAG = "dingetje";

    /* textureview */
    private static final int STATE_PREVIEW = 0;
    //private static final int STATE_WAITING_LOCK = 1;
    //private static final int STATE_WAITING_PRECAPTURE = 2;
    //private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    //private static final int STATE_PICTURE_TAKEN = 4;
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final int MAX_PREVIEW_HEIGHT = 1080;

    /* camera */
    private String camID;
    private AutoFitTextureView aTView;
    private CameraCaptureSession capSession;
    private CameraDevice camDev;
    private Size pvSize;

    /* handler */
    private HandlerThread bgDraad;
    private Handler bgHandler;

    /* capturerq */
    private CaptureRequest.Builder pvRqBuilder;
    private CaptureRequest pvRequest;
    private ImageReader imgReader;
    private int state = STATE_PREVIEW;
    private Semaphore camOCL = new Semaphore(1);
    private int senOrientation;

    /* idk */
    /* yolo */

    public CameraDing() {
        // Required empty public constructor
    }

    /* textureview */
    private final TextureView.SurfaceTextureListener sTListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    /* camera */
    private final CameraDevice.StateCallback stCall = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            camOCL.release();
            camDev = camera;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camOCL.release();
            camera.close();
            camDev = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camOCL.release();
            camera.close();
            camDev = null;
            Activity act = getActivity();

            if(null != act){
                act.finish();
            }
        }
    };

    /* capturesession */
    private CameraCaptureSession.CaptureCallback capCallBack = new CameraCaptureSession.CaptureCallback() {

        private void verwerk(CaptureResult res){
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult pResult) {
            verwerk(pResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            verwerk(result);
        }
    };

    private void showToast(final String text){
        final Activity act = getActivity();

        if(act != null){
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(act, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /* preview */

    private static Size optimalSize(Size[] choices, int tvW, int tvH, int maxW, int maxH, Size aspRatio){

        List<Size> bigEnough = new ArrayList<>();
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspRatio.getWidth();
        int h = aspRatio.getHeight();

        for (Size option : choices){
            if(option.getWidth() <= maxW && option.getHeight() <= maxH * h / w){
                if(option.getWidth() >= tvW && option.getHeight() >= tvH){
                    bigEnough.add(option);
                }
            } else{
                notBigEnough.add(option);
            }
        }

        if(bigEnough.size() > 0){
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if(notBigEnough.size() > 0){
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else{
            Log.wtf(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    /* handler */
    @Override
    public void onResume(){
        super.onResume();
        startBackgroundThread();

        if(aTView.isAvailable()){
            openCamera(aTView.getWidth(), aTView.getHeight());
        } else{
            aTView.setSurfaceTextureListener(sTListener);
        }

    }

    @Override
    public void onPause(){
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    private final ImageReader.OnImageAvailableListener imgAvListener
            = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {

        }
    };


    /* camera permission */
    private void requestCameraPermission(){
        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
            new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
        } else{
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);

        }
    }

    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantRes){
        if(reqCode == REQUEST_CAMERA_PERMISSION){
            if(grantRes.length != 1 || grantRes[0] != PackageManager.PERMISSION_GRANTED){
                ErrorDialog.newInstance(getString(R.string.request_permission)).show(getChildFragmentManager(), FRAGMENT_DIALOG);
            }
        } else{
            super.onRequestPermissionsResult(reqCode, permissions, grantRes);
        }
    }

    /* Camera manager */
    private void setUpCameraOutputs(int w, int h){
        Activity act = getActivity();
        CameraManager manager = (CameraManager)act.getSystemService(Context.CAMERA_SERVICE);

        try {

            for(String cameraID : manager.getCameraIdList()){
                CameraCharacteristics character = manager.getCameraCharacteristics(cameraID);

                Integer bfCam = character.get(CameraCharacteristics.LENS_FACING);
                if(bfCam != null && bfCam == CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }

                StreamConfigurationMap map = character.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if(map == null){
                    continue;
                }

                Size big = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
                imgReader = ImageReader.newInstance(big.getWidth() / 16, big.getHeight() / 16, ImageFormat.JPEG, 2);
                imgReader.setOnImageAvailableListener(imgAvListener, bgHandler);

                int dispRot = act.getWindowManager().getDefaultDisplay().getRotation();
                senOrientation = character.get(CameraCharacteristics.SENSOR_ORIENTATION);
                boolean omgekeerd = false;

                switch(dispRot){
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        if(senOrientation == 90 || senOrientation == 270){
                            omgekeerd = true;
                        }
                        break;
                        case Surface.ROTATION_90:
                        case Surface.ROTATION_270:
                            if(senOrientation == 0 || senOrientation == 180){
                                omgekeerd = true;
                            }
                            break;
                        default:
                            Log.w(TAG, "Display rotation is invalid: " + dispRot);
                }

                Point dispSz = new Point();
                act.getWindowManager().getDefaultDisplay().getSize(dispSz);
                int rotPvW = w;
                int rotPvH = h;
                int maxPvW = dispSz.x;
                int maxPvH = dispSz.y;


                if (omgekeerd){
                    rotPvW = h;
                    rotPvH = w;
                    maxPvW = dispSz.y;
                    maxPvH = dispSz.x;
                }

                if(maxPvW > MAX_PREVIEW_WIDTH){
                    maxPvW = MAX_PREVIEW_WIDTH;
                }

                if(maxPvH > MAX_PREVIEW_HEIGHT){
                    maxPvH = MAX_PREVIEW_HEIGHT;
                }

                pvSize = optimalSize(map.getOutputSizes(SurfaceTexture.class), rotPvW, rotPvH, maxPvW, maxPvH, big);

                int orientation = getResources().getConfiguration().orientation;
                if(orientation == Configuration.ORIENTATION_PORTRAIT){
                    aTView.setAspectRatio(pvSize.getWidth(), pvSize.getHeight());
                } else{
                    aTView.setAspectRatio(pvSize.getHeight(), pvSize.getWidth());
                }

               camID = cameraID;
                return;
            }

        } catch(CameraAccessException cx){
            cx.printStackTrace();

        } catch(NullPointerException ex){
            ex.printStackTrace();
            ErrorDialog.newInstance(getString(R.string.camera_error)).show(getChildFragmentManager(), FRAGMENT_DIALOG);
        }
    }

    /* camera */
    private void openCamera(int w, int h){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestCameraPermission();
            return;
        }

        setUpCameraOutputs(w, h);
        configureTransform(w, h);
        Activity act = getActivity();
        CameraManager manager = (CameraManager)act.getSystemService(Context.CAMERA_SERVICE);

        try {
            if(!camOCL.tryAcquire(2500, TimeUnit.MILLISECONDS)){
                throw new RuntimeException("Time out.");
            }
            manager.openCamera(camID, stCall, bgHandler);
        } catch(CameraAccessException cx){
            cx.printStackTrace();
        } catch(InterruptedException ix){
            throw new RuntimeException("Interrupted while trying to lock camera", ix);
        }
    }

    private void closeCamera(){
        try {
            camOCL.acquire();
            if(null != capSession){
                capSession.close();
                capSession = null;
            }

            if(null != camDev){
                camDev.close();
                camDev = null;
            }

            if(null != imgReader){
                imgReader.close();
                imgReader = null;
            }
        } catch(InterruptedException ix){
            throw new RuntimeException("Interrupted while trying to lock camera closing", ix);
        } finally {
            camOCL.release();
        }
    }

    /* handlers */
    private void startBackgroundThread(){
        bgDraad = new HandlerThread("CameraBG");
        bgDraad.start();
        bgHandler = new Handler(bgDraad.getLooper());
    }

    private void stopBackgroundThread(){
        bgDraad.quitSafely();

        try {
            bgDraad.join();
            bgDraad = null;
            bgHandler = null;
        } catch(InterruptedException ix){
            ix.printStackTrace();
        }
    }

    /* camera preview session */
    private void createCameraPreviewSession(){
        try {
            SurfaceTexture tx = aTView.getSurfaceTexture();
            assert tx != null;

            tx.setDefaultBufferSize(pvSize.getWidth(), pvSize.getHeight());
            Surface surf = new Surface(tx);

            pvRqBuilder = camDev.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            pvRqBuilder.addTarget(surf);

            camDev.createCaptureSession(Arrays.asList(surf, imgReader.getSurface()), new CameraCaptureSession.StateCallback(){

                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if(null == camDev){
                        return;
                    }

                    capSession = session;

                    try {
                        pvRqBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

                        pvRequest = pvRqBuilder.build();
                        capSession.setRepeatingRequest(pvRequest, capCallBack, bgHandler);
                    } catch(CameraAccessException cx){
                        cx.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    showToast("Phail");
                }
                }, null
            );
        } catch(CameraAccessException cx){
            cx.printStackTrace();
        }
    }

    private void configureTransform(int vW, int vH){
        Activity act = getActivity();

        if(null == aTView || null == pvSize || null == act){
            return;
        }

        int rot = act.getWindowManager().getDefaultDisplay().getRotation();
        Matrix mx = new Matrix();
        RectF vwRect = new RectF(0, 0, vW, vH);
        RectF bfRect = new RectF(0, 0, pvSize.getHeight(), pvSize.getWidth());
        float cntrX = vwRect.centerX();
        float cntrY = vwRect.centerY();

        if(Surface.ROTATION_90 == rot || Surface.ROTATION_270 == rot){
            bfRect.offset(cntrX - bfRect.centerX(), cntrY - bfRect.centerY());
            mx.setRectToRect(vwRect, bfRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max((float)vH / pvSize.getHeight(), (float)vW / pvSize.getWidth());
            mx.postScale(scale, scale, cntrX, cntrY);
            mx.postRotate(90 * (rot - 2), cntrX, cntrY);
        } else if(Surface.ROTATION_180 == rot){
            mx.postRotate(180, cntrX, cntrY);
        }

        aTView .setTransform(mx);
    }

    private int getSenOrientation(int rot){
        SparseIntArray orientations = new SparseIntArray();
        orientations.append(Surface.ROTATION_0, 90);
        orientations.append(Surface.ROTATION_90, 0);
        orientations.append(Surface.ROTATION_180, 270);
        orientations.append(Surface.ROTATION_270, 180);

        return (orientations.get(rot) + senOrientation + 270) % 360;
    }

    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size o1, Size o2) {
            return Long.signum((long)o1.getWidth() * o1.getHeight() - (long) o2.getWidth() * o2.getHeight());
        }
    }

    /* dialog prompts */

    public static class ErrorDialog extends DialogFragment{
        public static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message){
            ErrorDialog dlg = new ErrorDialog();
            Bundle args = new Bundle();
            dlg.setArguments(args);
            return dlg;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Activity act = getActivity();

            return new AlertDialog.Builder(act).setMessage(getArguments().getString(ARG_MESSAGE)).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    act.finish();
                }
            }).create();
        }
    }

    public static class ConfirmationDialog extends DialogFragment{

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Activity act = getActivity();

            return new AlertDialog.Builder(act).setMessage(R.string.request_permission).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getParentFragment().requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    act.finish();
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Activity act = getParentFragment().getActivity();
                    if(act != null){
                        act.finish();
                    }
                }
            }).create();
        }
    }

    public static CameraDing newInstance() {
        return new CameraDing();
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

}
