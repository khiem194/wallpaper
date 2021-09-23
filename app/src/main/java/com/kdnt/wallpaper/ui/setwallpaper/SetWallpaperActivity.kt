package com.kdnt.wallpaper.ui.setwallpaper

import android.app.ProgressDialog
import android.app.WallpaperManager
import android.os.Bundle
import android.os.Environment
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.downloader.Error
import com.downloader.OnCancelListener
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ActivitySetWallpaperBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException


class SetWallpaperActivity : BaseActivity<SetWallpaperViewModel, ActivitySetWallpaperBinding>() {
    private lateinit var url: String
    override fun getLayoutResId(): Int = R.layout.activity_set_wallpaper

    override val mViewModel: SetWallpaperViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photoModel = intent.extras?.get(WallpaperUtils.KEY_PHOTOS) as PhotoModel
        url = photoModel.src.large2x
        Glide.with(this)
            .load(url)
            .thumbnail(0.1f)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .dontAnimate()
            .into(mViewBinding.ivPhoto)
        mViewBinding.btnSetWallpaper.setOnClickListener {
            setWallpaper()
        }
        PRDownloader.initialize(applicationContext);

        mViewBinding.imageView2.setOnClickListener {
            checkPermissionImage()
        }
    }

    private fun checkPermissionImage() {
        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        downloadImage()
                    } else {
                        Toast.makeText(
                            this@SetWallpaperActivity,
                            "Please allow all permission!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {

                }
            }).check()
    }

    private fun downloadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.show()
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        PRDownloader.download(url, file.path, URLUtil.guessFileName(url, null, null))
            .build()
            .setOnStartOrResumeListener { }
            .setOnPauseListener { }
            .setOnCancelListener {

            }
            .setOnProgressListener {
                val per = it.currentBytes * 100 / it.totalBytes
                progressDialog.setMessage("Downloading: $per%")
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@SetWallpaperActivity,
                        "Download Complete!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        this@SetWallpaperActivity,
                        "Error!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun setWallpaper() {
        //this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN )
        val wallpaperManager = WallpaperManager.getInstance(this)
        try {
            val bitmap = mViewBinding.ivPhoto.drawable.toBitmap()
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this, "Set Wallpaper Done!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Can not set Wallpaper!", Toast.LENGTH_SHORT).show()
        }
    }
}