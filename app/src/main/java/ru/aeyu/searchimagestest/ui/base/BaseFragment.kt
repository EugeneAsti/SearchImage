package ru.aeyu.searchimagestest.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.aeyu.searchimagestest.BuildConfig

abstract class BaseFragment<
        FragmentState: FragmentListenableState,
        FragmentEffect: FragmentListenableEffects,
        BindingClass : ViewBinding,
        MyViewModel: BaseViewModel<FragmentState, FragmentEffect>> : Fragment() {

    protected abstract val viewModel: MyViewModel
    private var _binding: BindingClass? = null

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getBindingInstance(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState()
        collectEffects()
    }

    abstract fun getBindingInstance(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    ): BindingClass?

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setMessage(text)
            setNeutralButton("Понятно", null)
        }.show()
    }

    private fun showSnackBar(messageText: String) {
        Snackbar.make(requireView(), messageText, Snackbar.LENGTH_SHORT).show()
    }

    private fun collectEffects(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.fragmentEffectsListener.collect{
                    handleEffects(it)
                }
            }
        }
    }

    private fun collectState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.fragmentStateListener.collect{
                    handleState(it)
                }
            }
        }
    }

    protected abstract fun handleEffects(effect: FragmentEffect)
    protected abstract fun handleState(state: FragmentState)

    protected fun printLog(text: String){
        if(BuildConfig.DEBUG){
            Log.i("!!!###!!!", "[${this.javaClass.simpleName}] $text")
        }
    }

    protected fun showErrorAlertDialog(errMessage: String){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Ошибка")
            setMessage(errMessage)
            setCancelable(false)
            setNegativeButton("Понятно", null)
        }.show()
    }

    protected fun showToast(toastMessage: String){
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_LONG).show()
    }
}