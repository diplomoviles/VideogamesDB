package com.amaurypm.videogamesdb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.amaurypm.videogamesdb.databinding.GameDialogBinding
import com.google.android.material.textfield.TextInputEditText

class GameDialog: DialogFragment(){

    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: Dialog

    private var positiveButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = GameDialogBinding.inflate(requireActivity().layoutInflater)

        //Instanciamos el dialog
        dialog = AlertDialog.Builder(requireContext()).setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton("Guardar"){ _, _ ->

            }
            .setNegativeButton("Cancelar"){ _, _ ->

            }
            .create()

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


}