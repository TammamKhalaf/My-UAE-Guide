package com.tammamkhalaf.myuaeguide.chat.helperClass

import androidx.fragment.app.DialogFragment

class ChangePhotoDialog : DialogFragment() {
    interface OnPhotoReceivedListener {
        fun getImagePath(imagePath: android.net.Uri?)
        fun getImageBitmap(bitmap: android.graphics.Bitmap?)
    }

    var mOnPhotoReceived: OnPhotoReceivedListener? = null
    @Nullable
    fun onCreateView(inflater: android.view.LayoutInflater, @Nullable container: android.view.ViewGroup?, @Nullable savedInstanceState: android.os.Bundle?): android.view.View {
        val view: android.view.View = inflater.inflate(R.layout.dialog_changephoto, container, false)

        //Initialize the textview for choosing an image from memory
        val selectPhoto: android.widget.TextView = view.findViewById<android.widget.TextView>(R.id.dialogChoosePhoto)
        selectPhoto.setOnClickListener(object : android.view.View.OnClickListener() {
            override fun onClick(v: android.view.View) {
                android.util.Log.d(TAG, "onClick: accessing phones memory.")
                val intent: android.content.Intent = android.content.Intent(android.content.Intent.ACTION_GET_CONTENT)
                intent.setType("image/*")
                startActivityForResult(intent, PICKFILE_REQUEST_CODE)
            }
        })

        //Initialize the textview for choosing an image from memory
        val takePhoto: android.widget.TextView = view.findViewById<android.widget.TextView>(R.id.dialogOpenCamera)
        takePhoto.setOnClickListener(object : android.view.View.OnClickListener() {
            override fun onClick(v: android.view.View) {
                android.util.Log.d(TAG, "onClick: starting camera")
                val cameraIntent: android.content.Intent = android.content.Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
            }
        })
        return view
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: android.content.Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        /*
        Results when selecting new image from phone memory
         */if (requestCode == PICKFILE_REQUEST_CODE && resultCode == android.app.Activity.RESULT_OK) {
            val selectedImageUri: android.net.Uri = data.getData()
            android.util.Log.d(TAG, "onActivityResult: image: $selectedImageUri")

            //send the bitmap and fragment to the interface
            mOnPhotoReceived!!.getImagePath(selectedImageUri)
            getDialog().dismiss()
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == android.app.Activity.RESULT_OK) {
            android.util.Log.d(TAG, "onActivityResult: done taking a photo.")
            val bitmap: android.graphics.Bitmap
            bitmap = data.getExtras().get("data") as android.graphics.Bitmap
            mOnPhotoReceived!!.getImageBitmap(bitmap)
            getDialog().dismiss()
        }
    }

    fun onAttach(context: android.content.Context?) {
        try {
            mOnPhotoReceived = getActivity() as OnPhotoReceivedListener?
        } catch (e: java.lang.ClassCastException) {
            android.util.Log.e(TAG, "onAttach: ClassCastException", e.cause)
        }
        super.onAttach(context)
    }

    companion object {
        private const val TAG = "ChangePhotoDialog"
        const val CAMERA_REQUEST_CODE = 5467 //random number
        const val PICKFILE_REQUEST_CODE = 8352 //random number
    }
}