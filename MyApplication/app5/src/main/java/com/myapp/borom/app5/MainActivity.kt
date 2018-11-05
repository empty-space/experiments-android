package com.myapp.borom.app5

import android.app.DownloadManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.os.HandlerCompat.postDelayed
import android.os.Looper
import android.support.annotation.Nullable
import android.view.View
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.inputmethod.InputMethodManager
import java.io.File


const val EDIT_TEXT_DELAY_MS:Long = 500

class MainActivity : AppCompatActivity() {

    private var viewModel: FileLoadingViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("test","oncreatetest")
        //ViewModel
        viewModel = ViewModelProviders.of(this).get(FileLoadingViewModel::class.java)
        viewModel?.downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager;

        //buttons observation
        viewModel?.uiState?.observe(this, object : Observer<UIState> {
            override fun onChanged(@Nullable state: UIState?) {
                refreshUi(state)
            }
        })

        //edit_text CHANGED reaction with DELAY
        txt_file_id.addTextChangedListener(object:TextWatcher {
            var handler = Handler();
            var workRunnable: Runnable? = null
            override fun afterTextChanged(s: Editable) {
                handler.removeCallbacks(workRunnable)
                workRunnable = Runnable(){ onChanged(s.toString()) }
                handler.postDelayed(workRunnable, EDIT_TEXT_DELAY_MS /*delay*/)
            }
            fun onChanged(s:String) {
                viewModel?.fileId?.value = s
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        });
        txt_file_id.text = txt_file_id.text
    }

    private fun refreshUi(state: UIState? = viewModel?.uiState?.value) {
        btn_download.isEnabled=false
        btn_open.isEnabled=false
        btn_delete.isEnabled=false
        when(state){
            UIState.INVALID_INPUT->{}
            UIState.CAN_DOWNLOAD->{
                btn_download.isEnabled=true
            }
            UIState.DOWNLOADED->{
                btn_open.isEnabled=true
                btn_delete.isEnabled=true
            }
        }
    }

    fun hideKeyboard(v:View){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),0)
    }
    fun download(v: View) {
        hideKeyboard(v)
        viewModel!!.download()
    }
    fun open(v: View) {
        hideKeyboard(v)
        //viewModel?.open()
        val file = File(filesDir.path + "/" + viewModel?.getFileName())
        var target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        val intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch ( e: ActivityNotFoundException) {
            viewModel?.showToast("There is no PDF reader")
        }
    }
    fun delete(v: View) {
        hideKeyboard(v)
        viewModel?.delete()
    }
}
