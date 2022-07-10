package com.example.shoestoreinventory

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestoreinventory.models.Shoe

class SharedViewModel : ViewModel() {
    /**
     * Login
     * **/
    private val loginPress = MutableLiveData(false)
    val eventLoginPress: LiveData<Boolean>
        get() = loginPress

    fun onLoginPress() {
        loginPress.value = true

    }

    fun onLoginPressComplete() {
        loginPress.value = false
    }


    /**
     * Register
     * **/
    private val registerPress = MutableLiveData(false)
    val eventregisterPress: LiveData<Boolean>
        get() = registerPress

    fun onRegisterPress() {
        registerPress.value = true

    }

    fun onRegisterPressComplete() {
        registerPress.value = false
    }



    /**
     * Welcome
     * **/
    private val Welcome = MutableLiveData(false)
    val eventWelcome: LiveData<Boolean>
        get() = Welcome

    fun onWelcome() {
        Welcome.value = true

    }

    fun onWelcomeComplete() {
        Welcome.value = false
    }



    /**
     * Instructions
     * **/
    private val instructions = MutableLiveData(false)
    val eventInstructions: LiveData<Boolean>
        get() = instructions

    fun onInstructions() {
        instructions.value = true

    }

    fun onInstructionsComplete() {
        instructions.value = false
    }



    /**
     * shoe list
     **/

    private val addShoePress = MutableLiveData(false)
    val eventAddShoePress: LiveData<Boolean>
        get() = addShoePress

    fun onAddShoePress() {
        addShoePress.value = true
    }

    fun onAddShoePressComplete() {
        addShoePress.value = false
    }


    /**
     *shoe detail
     * */
    private val saveShoePress = MutableLiveData(false)
    val eventSaveShoePress: LiveData<Boolean>
        get() = saveShoePress

    var shoe = Shoe("", "", "", "", Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888))
    private var _shoesList = MutableLiveData<MutableList<Shoe>>(mutableListOf())
    val shoesList: LiveData<MutableList<Shoe>>
        get() = _shoesList

    private val saveShoeError = MutableLiveData("")
    val eventSaveShoeError: LiveData<String>
        get() = saveShoeError

    fun onSaveShoePress() {
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
    fun setError(error: String) {
        saveShoeError.value = error
    }
    fun clearError(){
        saveShoeError.value=""
    }
    fun saveNewShoe(){
        saveShoePress.value = true
        val list = _shoesList.value
        list?.let {
            it.add(shoe)
            _shoesList.setValue(it)
        }
    }
    fun onSaveShoePressComplete() {
        saveShoePress.value = false
    }

    private val cancelPress = MutableLiveData<Boolean>(false)
    val eventCancelPress: LiveData<Boolean>
        get() = cancelPress
    private val shoeImageChange = MutableLiveData<Boolean>(false)
    val eventShoeImageChange: LiveData<Boolean>
        get() = shoeImageChange
    private var shoeImageINdex=MutableLiveData(-1)
    val shoeImageIndex: LiveData<Int>
        get() = shoeImageINdex
    private val takeShoeImage = MutableLiveData<Boolean>(false)
    val eventTakeShoeImage: LiveData<Boolean>
        get() = takeShoeImage

    fun onCancelPress() {
        cancelPress.value = true

    }

    fun onCancelPressComplete() {
        cancelPress.value = false
    }

    fun onTakeShoeImage(){
       takeShoeImage.value = true
    }
    fun onTakeShoeImageComplete(){
        takeShoeImage.value = false
    }
    fun onShoeImageChanged(view: View){
        when(shoeImageINdex.value){
            4->{
                shoeImageINdex.value=0
            }
            else->{
                shoeImageINdex.value=shoeImageINdex.value?.plus(1)
            }

        }
        shoeImageChange.value = true
    }
    fun onShoeImageChangedComplete(view:View){
        shoe.image= (view as ImageView).drawable.toBitmap()
        shoeImageChange.value = false

    }

    fun clearShoe() {
        shoe = Shoe("", "", "", "", Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888))
    }

}
