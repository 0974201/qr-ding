package com.example.QRdinggevalzooi.ui.mainactivity3;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.QRdinggevalzooi.AutoFitTextureView;
import com.example.QRdinggevalzooi.MainActivity3;
import com.example.QRdinggevalzooi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MainActivity3Fragment extends Fragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private MainActivity3ViewModel mViewModel;

    private AutoFitTextureView texView;
    private Handler hand;
    private HandlerThread handT;
    private String camID;
    private CameraDevice camDev;
    private CaptureRequest.Builder capRQB;
    private CaptureRequest capRQ;
    private CameraCaptureSession camCapSes;
    private ImageReader imgR;
    private int imgF = ImageFormat.YUV_420_888;

    private Semaphore camDinges = new Semaphore(1);
    private Semaphore camPlaatje = new Semaphore(1);
    private Size prevSize;
    private Rect rekt;
    private Rect prevRekt;
    private int sensOrient;
    private final int reqCamPermissie = 1;
    private final String FRAGMENT_DIALOG = "dialog";


    public static MainActivity3Fragment newInstance() {
        return new MainActivity3Fragment();
    }

    private final TextureView.SurfaceTextureListener surfTL = new TextureView.SurfaceTextureListener(){

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int w, int h){
            openCamera(w, h);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int w, int h){
            configueTransform(w, h);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture){
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture){
        }
    };

    private final CameraDevice.StateCallback q = new CameraDevice.StateCallback(){

        @Override
        public void onOpened(@androidx.annotation.NonNull CameraDevice camera) {

            camDinges.release();
            camDev = camera;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@androidx.annotation.NonNull CameraDevice camera) {

            camDinges.release();
            camera.close();
            camDev = null;
        }

        @Override
        public void onError(@androidx.annotation.NonNull CameraDevice camera, int error) {

            camDinges.release();
            camera.close();
            camDev = null;

            Activity act = getActivity();
            if(null != act){
                act.finish();
            }

        }
    };

    private final ImageReader.OnImageAvailableListener imgDingetje = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            //woop alles uit activity2 hier copypasta'en
        }
    };

    private CameraCaptureSession.CaptureCallback capCall = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result){

        }

        @Override
        public void onCaptureProgressed(@androidx.annotation.NonNull CameraCaptureSession session, @androidx.annotation.NonNull CaptureRequest request, @androidx.annotation.NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureCompleted(@androidx.annotation.NonNull CameraCaptureSession session, @androidx.annotation.NonNull CaptureRequest request, @androidx.annotation.NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
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

    private static Size chooseOptimalSize(Size[] choices, int textVW, int textVH, int maxW, int maxH, Size aspectRatio){

        List<Size> bigEnough = new ArrayList<>();
        List<Size> notBigEnough = new ArrayList<>();

        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();

        for(Size option : choices){

            if(option.getWidth() <= maxW && option.getHeight() <= maxH && option.getHeight() == option.getWidth() * h / w){

                if(option.getWidth() >= textVW && option.getHeight() >= textVH){
                    bigEnough.add(option);
                }
            } else{
                notBigEnough.add(option);
            }
        }

        if(bigEnough.size() > 0){
            Collections.min(bigEnough, new CompareSizesByArea());
        } else if(notBigEnough.size() > 0){
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            Log.wtf("", "");
            return choices[0];
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_activity3_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainActivity3ViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPause() {

        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
        startBackgroundThread();

        if(texView.isAvailable()){
            openCamera(texView.getWidth(), texView.getHeight());
        } else{
            texView.setSurfaceTextureListener(surfTL);
        }
    }

    private void reqCamPermissie(){

        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
            new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, reqCamPermissie);
        }
    }

    public void onRequestPermissionsResult(int reqCode, @NonNull String[] perms, @NonNull int[] grantRes){

        if(reqCode == reqCamPermissie){
            if(grantRes.length != 1 || grantRes[0] != PackageManager.PERMISSION_GRANTED){
                ErrorDialog.newInstance(getString(R.string.request_permission)).show(getChildFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(reqCode, perms, grantRes);
        }
    }

    private void setUpCameraOutputs(int width, int height){

        Activity act = getActivity();
        CameraManager camMan = (CameraManager) act.getSystemService(Context.CAMERA_SERVICE);

        try{
            for(String cameraID : camMan.getCameraIdList()){

                CameraCharacteristics karakter = camMan.getCameraCharacteristics(cameraID);
                Integer wut = karakter.get(CameraCharacteristics.LENS_FACING);
                if(wut != null && wut == CameraCharacteristics.LENS_FACING_FRONT){
                    continue;
                }

                StreamConfigurationMap uh = karakter.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if(uh = null){
                    continue;
                }

                Size largest = Collections.max(Arrays.asList(uh.getHighResolutionOutputSizes(imgF)), new CompareSizesByArea());
                imgR = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), imgF / 2);
                imgR.setOnImageAvailableListener(imgDingetje, hand);

                int rot = act.getWindowManager().getDefaultDisplay().getRotation();
                boolean ondersteboven = false;

                prevSize = chooseOptimalSize(uh.getOutputSizes(SurfaceTexture.class), w, h, largest);
                Log.wtf("", "");
                texView.setAspectRatio(MainActivity3.screenParametersPoint.x, MainActivity3.screenParametersPoint.y - getStatusBarHEight());


                camID = cameraID;
                return;

            }
        } catch (CameraAccessException cx){
            cx.printStackTrace();
        }
    }

    private void openCamera(int w, int h){

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            reqCamPermissie();
            return;
        }

        setUpCameraOutputs(w,h);
        configueTransform(w,h);
        Activity act = getActivity();
        CameraManager camMan = (CameraManager) act.getSystemService(Context.CAMERA_SERVICE);

        try {
            if(!camDinges.tryAcquire(2500, TimeUnit.MILLISECONDS)){
                throw new RuntimeException("");
            } camMan.openCamera(camID, );

        } catch (CameraAccessException cx){
            cx.printStackTrace();
        } catch (InterruptedException ix){
            throw new RuntimeException("u done goofed", ix);
        }
    }

    private void closeCamera(){

        try {
            camDinges.acquire();
            if(null != camCapSes){
                camCapSes.close();
                camCapSes = null;
            }

            if(null != camDev){
                camDev.close();
                camDev = null;
            }
        } catch (InterruptedException ix){
            throw new RuntimeException("u done goofed", ix);
        } finally {
            camDinges.release();
        }
    }

    private void startBackgroundThread(){

        handT = new HandlerThread("");
        handT.start();
        hand = new Handler(handT.getLooper());
    }

    private void stopBackgroundThread(){

        try {
            handT.quitSafely();
            handT.join();
            hand = null;
            handT = null;
        } catch(InterruptedException ix){
            ix.printStackTrace();
            hand = null;
            handT = null;

        } catch(NullPointerException nx){
            nx.printStackTrace();
            hand = null;
            handT = null;
        }
    }

    private void createCameraPreviewSession(){

        try {
            SurfaceTexture sTex = texView.getSurfaceTexture();
            assert sTex != null;

            sTex.setDefeaultBufferSize(prevSize.getWidth(), prevSize.getHeight());
            Log.wtf("", "");

            Surface surf = new Surface(sTex);

            capRQB = camDev.createCaptureRequest(camDev.TEMPLATE_PREVIEW);
            capRQB.addTarget(surf);

            camDev.createCaptureSession(Arrays.asList(surf, imgR.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@androidx.annotation.NonNull CameraCaptureSession session) {

                    if(null == camDev){
                        return;
                    }

                    camCapSes = session;
                    try{

                        capRQB.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        capRQ = capRQB.build();
                        camCapSes.setRepeatingRequest(capRQ,capCall,hand);
                    } catch(CameraAccessException cx){
                        cx.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@androidx.annotation.NonNull CameraCaptureSession session) {
                    showToast("Phail");
                }
            });

        } catch (CameraAccessException cx){
            cx.printStackTrace();
        }
    }

    private void configueTransform(int vWidth, int vHeight){

        Activity act = getActivity();

        if(null == texView || null = prevSize || null == act){
            return;
        }

        int rot = act.getWindowManager().getDefaultDisplay().getRotation();
        Matrix mtx = new Matrix();
        RectF vRekt = new RectF(0, 0, vWidth, vHeight);
        RectF bRekt = new RectF(0, 0, prevSize.getWidth(), prevSize.getHeight());
        float cnX = vRekt.centerX();
        float cnY = vRekt.centerY();

        if (Surface.ROTATION_90 == rot || Surface.ROTATION_270 == rot){

            bRekt.offset(cnX - bRekt.centerX(), cnY - bRekt.centerY());
            mtx.setRectToRect(vRekt, bRekt, Matrix.ScaleToFit.FILL);
            float scale = Math.max((float)vHeight / prevSize.getHeight(), (float)vWidth / prevSize.getWidth());
            mtx.postScale(scale, scale, cnX, cnY);
            mtx.postRotate(90 * (rot - 2), cnX, cnY);
        } else if(Surface.ROTATION_180 == rot){

            mtx.postRotate(180, cnX, cnY);
        }

        texView.setTransform(mtx);
    }

    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs){
            return Long.signum((long)lhs.getWidth() * lhs.getHeight() - (long)rhs.getWidth() * rhs.getHeight());
        }
    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message){
            ErrorDialog eDialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            eDialog.setArguments(args);
            return eDialog;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Activity act = getActivity();

            return new AlertDialog.Builder(act).setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dIF, int i){
                            act.finish();
                        }
                    })
            .create();
        }
    }

    public static class ConfirmationDialog extends DialogFragment {

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Fragment pt = getParentFragment();

            return new AlertDialog.Builder(getActivity()).setMessage(R.string.request_permission)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            pt.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            Activity act = pt.getActivity();
                            if(act != null){
                                act.finish();
                            }
                        }
                    })
                    .create();
        }
    }

}
