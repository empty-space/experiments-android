package com.myapp.borom.app5

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import android.support.annotation.Nullable
import android.view.View
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.inputmethod.InputMethodManager
import java.io.File
import android.support.v4.content.FileProvider.getUriForFile
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager


const val EDIT_TEXT_DELAY_MS:Long = 500
const val SHOW_AGAIN_PREFERENCE = "dont_show_again"
class MainActivity : AppCompatActivity() {

    private var viewModel: FileLoadingViewModel?= null

    fun preferences() =  PreferenceManager.getDefaultSharedPreferences(baseContext)
    var alreadyShownPreference = false
    var showAgainPreference:Boolean
        get() = !alreadyShownPreference
                && !preferences().getBoolean(SHOW_AGAIN_PREFERENCE, false)
        set(value){
            var x =  preferences().edit()
            x.putBoolean(SHOW_AGAIN_PREFERENCE,value)
            x.apply()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("test","oncreatetest")
        //ViewModel
        viewModel = ViewModelProviders.of(this).get(FileLoadingViewModel::class.java)

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

    override fun onStart() {
        super.onStart()
        if(showAgainPreference) {
            alreadyShownPreference = true
            startActivity(Intent(this, MyPreferencesActivity::class.java))
        }
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

    fun backgroundOnClick(v:View){
        hideKeyboard(v)
        startActivity(Intent(this, MyPreferencesActivity::class.java))
        //showPopup()
    }

    fun hideKeyboard(v:View){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.getWindowToken(),0)
    }
    fun download(v: View) {
        hideKeyboard(v)
        viewModel!!.download()
    }
    fun open(v: View) {
        hideKeyboard(v)
        //viewModel?.open()
        val file = File(viewModel?.getFileAbsolutePath())
        Log.d("OPEN",file.absolutePath)
        val contentUri = getUriForFile(application.applicationContext, "com.myapp.borom.app5.fileprovider", file)
        Log.d("OPEN URI",contentUri.toString())
        var target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(contentUri,"application/pdf");
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

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

    fun showPopup(){
        // Initialize a new layout inflater instance
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.popup_about,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                LinearLayout.LayoutParams.MATCH_PARENT // Window height
        )

        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }

        // Get the widgets reference from custom view
        val tv = view.findViewById<TextView>(R.id.text_label)
        val buttonPopup = view.findViewById<Button>(R.id.button_popup)
        val checkBox = view.findViewById<Button>(R.id.check_box)

        // Set click listener for popup window's text view
        tv.setOnClickListener{
            // Change the text color of popup window's text view
            tv.setTextColor(Color.RED)
        }
        checkBox.setOnClickListener {
            //todo
            //showAgainPreference=false
        }

        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
        }

        // Set a dismiss listener for popup window
        popupWindow.setOnDismissListener {
            Toast.makeText(applicationContext,"Popup closed",Toast.LENGTH_SHORT).show()
        }


        // Finally, show the popup window on app
        //TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
                root_layout, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
        )
    }
}
