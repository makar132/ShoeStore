package com.example.shoestoreinventory

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestoreinventory.models.Shoe

class MainActivityViewModel : ViewModel() {
    /**
     * Login
     * **/
    private val login = MutableLiveData(false)
    val loginClicked: LiveData<Boolean>
        get() = login

    fun onLoginClick() {
        login.value = true

    }

    fun onLoginClickSuccess() {
        login.value = false
    }


    /**
     * Register
     * **/
    private val register = MutableLiveData(false)
    val registerClicked: LiveData<Boolean>
        get() = register

    fun onRegisterClick() {
        register.value = true

    }

    fun onRegisterClickSuccess() {
        register.value = false
    }



    /**
     * Welcome
     * **/
    private val welcomeNext = MutableLiveData(false)
    val welcomeNextClicked: LiveData<Boolean>
        get() = welcomeNext

    fun onWelcomeNextClick() {
        welcomeNext.value = true

    }

    fun onWelcomeNextClickSuccess() {
        welcomeNext.value = false
    }



    /**
     * Instructions
     * **/
    private val instructionsShoeListNext = MutableLiveData(false)
    val instructionsShoeListNextClicked: LiveData<Boolean>
        get() = instructionsShoeListNext

    fun onInstructionsShoeListNextClick() {
        instructionsShoeListNext.value = true

    }

    fun onInstructionsShoeListNextClickSuccess() {
        instructionsShoeListNext.value = false
    }

    private val instructionsShoeDetailGetStarted = MutableLiveData(false)
    val instructionsSoeDetailGetStartedClicked: LiveData<Boolean>
        get() = instructionsShoeDetailGetStarted

    fun onInstructionsShoeDetailGetStartedClick() {
        instructionsShoeDetailGetStarted.value = true

    }

    fun onInstructionsShoeDetailGetStartedClickSuccess() {
        instructionsShoeDetailGetStarted.value = false
    }



    /**
     * shoe list
     **/

    private val shoeListAddShoe = MutableLiveData(false)
    val shoeListAddShoeClicked: LiveData<Boolean>
        get() = shoeListAddShoe

    fun onShoeListAddShoeClick() {
        shoeListAddShoe.value = true
    }

    fun onShoeListAddShoeClickSuccess() {
        shoeListAddShoe.value = false
    }


    /**
     *shoe detail
     * */
    var shoe = Shoe("","", "", "", Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888))
    private var mutableShoesList = MutableLiveData<MutableList<Shoe>>(mutableListOf())
    val shoesList: LiveData<MutableList<Shoe>>
        get() = mutableShoesList

    private val shoeDetailSave = MutableLiveData(false)
    val shoeDetailSaveClicked: LiveData<Boolean>
        get() = shoeDetailSave


    private val shoeDetailSaveError = MutableLiveData("")
    val shoeDetailSaveErrorRaise: LiveData<String>
        get() = shoeDetailSaveError

    fun onShoeDetailSaveClick() {
        when{
            shoe.name.trim().isEmpty() -> {
                setError("Name is required")
            }
            shoe.company.trim().isEmpty() -> {
                setError("Company name is required")
            }
            shoe.size.trim().isEmpty() -> {
                setError("Size is required")
            }
            else -> {
                saveNewShoe()
            }
        }

    }
    private fun setError(error: String) {
        shoeDetailSaveError.value = error
    }
    fun onShoeDetailErrorRaiseSuccess(){
        shoeDetailSaveError.value=""
    }
    private fun saveNewShoe(){
        shoeDetailSave.value = true
        mutableShoesList.value?.add(shoe)
    }
    fun onShoeDetailSaveClickSuccess() {
        shoeDetailSave.value = false
    }

    private val shoeDetailCancel = MutableLiveData(false)
    val shoeDetailCancelClicked: LiveData<Boolean>
        get() = shoeDetailCancel
    private val shoeDetailChangeImage = MutableLiveData(false)
    val shoeDetailImageChanged: LiveData<Boolean>
        get() = shoeDetailChangeImage
    private var mutableShoeImageIndex=MutableLiveData(-1)
    val shoeImageIndex: LiveData<Int>
        get() = mutableShoeImageIndex
    private val shoeDetailTakeShoeImage = MutableLiveData(false)
    val shoeDetailShoeImageTaken: LiveData<Boolean>
        get() = shoeDetailTakeShoeImage

    fun onShoeDetailCancelClick() {
        shoeDetailCancel.value = true
    }

    fun onShoeDetailCancelClickSuccess() {
        shoeDetailCancel.value = false
    }

    fun onShoeDetailTakeShoeImageClick(){
       shoeDetailTakeShoeImage.value = true
    }
    fun onShoeDetailTakeShoeImageClickSuccess(){
        shoeDetailTakeShoeImage.value = false
    }
    fun onShoeDetailChooseFromSavedClick(view: View){
        when(mutableShoeImageIndex.value){
            4->{
                mutableShoeImageIndex.value=0
            }
            else->{
                mutableShoeImageIndex.value=mutableShoeImageIndex.value?.plus(1)
            }

        }
        shoeDetailChangeImage.value = true
    }
    fun onShoeImageChangedSuccess(view:View){
        shoe.image= (view as ImageView).drawable.toBitmap()
        shoeDetailChangeImage.value = false

    }

    fun clearShoe() {
        shoe = Shoe("", "", "", "", Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888))
    }

}
