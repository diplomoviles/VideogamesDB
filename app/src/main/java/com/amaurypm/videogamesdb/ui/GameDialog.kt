package com.amaurypm.videogamesdb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.amaurypm.videogamesdb.R
import com.amaurypm.videogamesdb.application.VideogamesDBApp
import com.amaurypm.videogamesdb.data.GameRepository
import com.amaurypm.videogamesdb.data.db.model.GameEntity
import com.amaurypm.videogamesdb.databinding.GameDialogBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

class GameDialog(
    private val newGame: Boolean = true,
    private var game: GameEntity = GameEntity(title = "", genre = "", developer = ""),
    private val updateUI: () -> Unit
): DialogFragment(){

    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: Dialog

    private var positiveButton: Button? = null

    private lateinit var repository: GameRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = GameDialogBinding.inflate(requireActivity().layoutInflater)

        repository = (requireContext().applicationContext as VideogamesDBApp).repository

        binding.tietTitle.setText(game.title)
        binding.tietGenre.setText(game.genre)
        binding.tietDeveloper.setText(game.developer)


        //Instanciamos el dialog
        dialog = if(newGame)
            buildDialog("Aceptar", "Cancelar",{
                //Guardar

                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer = binding.tietDeveloper.text.toString()

                try{

                    lifecycleScope.launch {

                        val result = async{
                            repository.insertGame(game)
                        }

                        result.await()

                        updateUI()

                        Toast.makeText(
                            requireContext(),
                            "Juego guardado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar el juego",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },{
                //Cancelar

            })
        else
            buildDialog("Actualizar", "Borrar",{
                //Actualizar

                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer = binding.tietDeveloper.text.toString()

                try{

                    lifecycleScope.launch {

                        val result = async{
                            repository.updateGame(game)
                        }

                        result.await()

                        updateUI()

                        Toast.makeText(
                            requireContext(),
                            "Juego actualizado exitosamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Error al actualizar el juego",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },{
                //Borrar

                val context = requireContext()

                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.confirmation))
                    .setMessage(getString(R.string.request_confirmation, game.title, "?"))
                    .setPositiveButton("Aceptar"){ _, _ ->
                        try{

                            lifecycleScope.launch {

                                val result = async{
                                    repository.deleteGame(game)
                                }

                                result.await()

                                updateUI()

                                Toast.makeText(
                                    context,
                                    context.getString(R.string.game_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }catch (e: IOException){
                            e.printStackTrace()
                            Toast.makeText(
                                context,
                                "Error al eliminar el juego",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.setNegativeButton("Cancelar"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            })


        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Este es cuando está ya el diálogo en pantalla
    override fun onStart() {
        super.onStart()

        positiveButton = (dialog as AlertDialog)
            .getButton(Dialog.BUTTON_POSITIVE)
        positiveButton?.isEnabled = false

        /*binding.tietTitle.addTextChangedListener {
            positiveButton?.isEnabled = validateFields()
        }

        binding.tietGenre.addTextChangedListener {
            positiveButton?.isEnabled = validateFields()
        }

        binding.tietDeveloper.addTextChangedListener {
            positiveButton?.isEnabled = validateFields()
        }*/

        setupTextChangedListener(
            binding.tietTitle,
            binding.tietGenre,
            binding.tietDeveloper
        )

    }

    private fun validateFields(): Boolean =
        binding.tietTitle.text.toString().isNotEmpty() &&
                binding.tietGenre.text.toString().isNotEmpty() &&
                binding.tietDeveloper.text.toString().isNotEmpty()

    private fun setupTextChangedListener(
        vararg textFields: TextInputEditText
    ){
        textFields.forEach { tiet ->
            tiet.addTextChangedListener{
                positiveButton?.isEnabled = validateFields()
            }
        }
    }

    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButtonClick: () -> Unit,
        negativeButtonClick: () -> Unit
    ): Dialog =
        AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton(btn1Text){ _, _ ->
                //Acción para el botón positivo
                positiveButtonClick()
            }
            .setNegativeButton(btn2Text){ _, _ ->
                //Acción para el botón negativo
                negativeButtonClick()
            }
            .create()


}