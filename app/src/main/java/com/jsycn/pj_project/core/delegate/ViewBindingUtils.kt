@file:JvmName("ViewBindingUtil")
@file:Suppress("UNCHECKED_CAST", "unused")
package com.jsycn.pj_project.core.delegate

/**
 *@Description:采用反射的方式，属性代理，简化使用
 * https://blog.csdn.net/c10WTiybQ1Ye3/article/details/112690188
 * https://github.com/DylanCaiCoding/ViewBindingKtx/wiki/%E4%BD%BF%E7%94%A8%E6%8B%93%E5%B1%95%E5%87%BD%E6%95%B0
 *@Author: jsync
 *@CreateDate: 2021/6/10 16:47
 */



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
    }.also { binding ->
//        if (this is ComponentActivity && binding is ViewDataBinding) {
//            binding.lifecycleOwner = this
//        }
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            .invoke(null, layoutInflater, parent, attachToParent) as VB
    }.also { binding ->
//        if (this is Fragment && binding is ViewDataBinding) {
//            binding.lifecycleOwner = viewLifecycleOwner
//        }
    }

@JvmName("inflateWithGeneric")
fun <VB : ViewBinding> Any.inflateBindingWithGeneric(parent: ViewGroup): VB =
    inflateBindingWithGeneric(LayoutInflater.from(parent.context), parent, false)

fun <VB : ViewBinding> Any.bindViewWithGeneric(view: View): VB =
    withGenericBindingClass<VB>(this) { clazz ->
        clazz.getMethod("bind", View::class.java).invoke(null, view) as VB
    }.also { binding ->
//        if (this is Fragment && binding is ViewDataBinding) {
//            binding.lifecycleOwner = viewLifecycleOwner
//        }
    }

private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
    any.allParameterizedType.forEach { parameterizedType ->
        parameterizedType.actualTypeArguments.forEach {
            try {
                return block.invoke(it as Class<VB>)
            } catch (e: Exception) {
            }
        }
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}

private val Any.allParameterizedType: List<ParameterizedType>
    get() {
        val genericParameterizedType = mutableListOf<ParameterizedType>()
        var genericSuperclass = javaClass.genericSuperclass
        var superclass = javaClass.superclass
        while (superclass != null) {
            if (genericSuperclass is ParameterizedType) {
                genericParameterizedType.add(genericSuperclass)
            }
            genericSuperclass = superclass.genericSuperclass
            superclass = superclass.superclass
        }
        return genericParameterizedType
    }