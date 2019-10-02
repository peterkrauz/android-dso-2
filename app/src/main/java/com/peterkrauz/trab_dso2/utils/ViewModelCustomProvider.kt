package com.peterkrauz.trab_dso2.utils

/**
 * ViewModels are generally obtained by using the native class ViewModelProviders:
 *
 * ```kotlin
 * val viewModel = ViewModelProviders.of(activityOrFragment)[MyViewModel::class.java]
 * ```
 *
 * In this approach, the Android framework instantiates the ViewModel, if it doesn't exist already.
 * This instantiation does not use any non-empty constructor the ViewModel may have. It's possible
 * to instantiate a ViewModel with a non-empty constructor by using a factory:
 *
 * ```kotlin
 * val factory = object: ViewModelProvider.Factory {
 *     override fun <T: ViewModel?> create(modelClass: Class<T>): T {
 *         // Build a ViewModel that matches the provided modelClass.
 *     }
 * }
 *
 * val viewModel = ViewModelProviders.of(activityOrFragment, factory)[MyViewModel::class.java]
 * ```
 *
 * The methods provided in this file allow using the same mechanism to provide ViewModels, but abstract
 * the factory away. Instead, the instantiation of the ViewModel is delegated to a lambda expression:
 *
 * ```kotlin
 * // Call in the context of an activity or fragment.
 * val viewModel = provideViewModel {
 *     MyViewModel(arguments)
 * }
 * ```
 *
 * A ViewModel may also be lazily provided:
 *
 * ```kotlin
 * // Call in the context of an activity or fragment.
 * val viewModel by lazyViewModel {
 *     MyViewModel(arguments)
 * }
 * ```
 */
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelCustomProvider<VM : ViewModel>(private val vmClass: Class<VM>, creator: () -> VM) {

    private val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return creator.invoke() as T
        }
    }

    operator fun get(activity: FragmentActivity): VM = ViewModelProvider(activity, factory)[vmClass]
    operator fun get(fragment: Fragment): VM = ViewModelProvider(fragment, factory)[vmClass]
}

/**
 * Provide the existing ViewModel of the current activity. If the ViewModel doesn't exist,
 * a new one will be created by calling the provided `creator` lambda.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel = provideViewModel {
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.provideViewModel(activity: FragmentActivity = this, noinline creator: () -> VM): VM {
    return ViewModelCustomProvider(VM::class.java, creator)[activity]
}

/**
 * Provide the existing ViewModel of the current fragment. If the ViewModel doesn't exist,
 * a new one will be created by calling the provided `creator` lambda.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel = provideViewModel {
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> Fragment.provideViewModel(fragment: Fragment = this, noinline creator: () -> VM): VM {
    return ViewModelCustomProvider(VM::class.java, creator)[fragment]
}

/**
 * Provide the existing ViewModel of an activity. If the ViewModel doesn't exist,
 * a new one will be created by calling the provided `creator` lambda.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel = provideViewModel(requireActivity()) {
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> provideViewModel(activity: FragmentActivity, noinline creator: () -> VM): VM {
    return ViewModelCustomProvider(VM::class.java, creator)[activity]
}

/**
 * Provides a lazy reference to the existing ViewModel of the current activity. If the ViewModel
 * doesn't exist when that reference is first read, a new one will be created by calling the provided
 * `creator` lambda.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel by lazyViewModel {
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.lazyViewModel(activity: FragmentActivity = this, noinline creator: () -> VM): Lazy<VM> {
    return lazy { provideViewModel(activity, creator) }
}

/**
 * Provides a lazy reference to the existing ViewModel of the current fragment. If the ViewModel
 * doesn't exist when that reference is first read, a new one will be created by calling the provided
 * `creator` lambda.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel by lazyViewModel {
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> Fragment.lazyViewModel(fragment: Fragment = this, noinline creator: () -> VM): Lazy<VM> {
    return lazy { provideViewModel(fragment, creator) }
}

/**
 * Provides a lazy reference to the existing ViewModel of an activity. If the ViewModel
 * doesn't exist when that reference is first read, a new one will be created by calling the provided
 * `creator` lambda.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel by lazyViewModel(::requireActivity) {
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> lazyViewModel(activity: FragmentActivity, noinline creator: () -> VM): Lazy<VM> {
    return lazy { provideViewModel(activity, creator) }
}

/**
 * Provide the existing ViewModel of the current activity. If the ViewModel doesn't exist,
 * a new one will be created by calling the provided `creator` lambda. If an exception is thrown
 * during the call to `creator`, it is logged and null is returned.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel? = provideViewModelOrNull {
 *      val viewModelArguments = tryGetValues()!!
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.provideViewModelOrNull(activity: FragmentActivity = this, noinline creator: () -> VM): VM? {
    return try {
        ViewModelCustomProvider(VM::class.java, creator)[activity]
    } catch (ex: Throwable) {
        ex.printStackTrace()
        null
    }
}

/**
 * Provide the existing ViewModel of the current fragment. If the ViewModel doesn't exist,
 * a new one will be created by calling the provided `creator` lambda. If an exception is thrown
 * during the call to `creator`, it is logged and null is returned.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel? = provideViewModelOrNull {
 *      val viewModelArguments = tryGetValues()!!
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> Fragment.provideViewModelOrNull(fragment: Fragment = this, noinline creator: () -> VM): VM? {
    return try {
        ViewModelCustomProvider(VM::class.java, creator)[fragment]
    } catch (ex: Throwable) {
        ex.printStackTrace()
        null
    }
}

/**
 * Provide the existing ViewModel of an activity. If the ViewModel doesn't exist,
 * a new one will be created by calling the provided `creator` lambda. If an exception is thrown
 * during the call to `creator`, it is logged and null is returned.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel? = provideViewModelOrNull(requireActivity()) {
 *      val viewModelArguments = tryGetValues()!!
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> provideViewModelOrNull(activity: FragmentActivity, noinline creator: () -> VM): VM? {
    return try {
        ViewModelCustomProvider(VM::class.java, creator)[activity]
    } catch (ex: Throwable) {
        ex.printStackTrace()
        null
    }
}

/**
 * Provides a lazy reference to the existing ViewModel of the current activity. If the ViewModel
 * doesn't exist when that reference is first read, a new one will be created by calling the provided
 * `creator` lambda. If an exception is thrown during the call to `creator`, it is logged and null is returned.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel? by lazyViewModelOrNull {
 *      val viewModelArguments = tryGetValues()!!
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.lazyViewModelOrNull(activity: FragmentActivity = this, noinline creator: () -> VM): Lazy<VM?> {
    return lazy { provideViewModelOrNull(activity, creator) }
}

/**
 * Provides a lazy reference to the existing ViewModel of the current fragment. If the ViewModel
 * doesn't exist when that reference is first read, a new one will be created by calling the provided
 * `creator` lambda. If an exception is thrown during the call to `creator`, it is logged and null is returned.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel? by lazyViewModelOrNull {
 *      val viewModelArguments = tryGetValues()!!
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> Fragment.lazyViewModelOrNull(fragment: Fragment = this, noinline creator: () -> VM): Lazy<VM?> {
    return lazy { provideViewModelOrNull(fragment, creator) }
}

/**
 * Provides a lazy reference to the existing ViewModel of an activity. If the ViewModel
 * doesn't exist when that reference is first read, a new one will be created by calling the provided
 * `creator` lambda. If an exception is thrown during the call to `creator`, it is logged and null is returned.
 *
 * Usage:
 *
 * ```kotlin
 * val viewModel: MyViewModel? by lazyViewModelOrNull(::requireActivity) {
 *      val viewModelArguments = tryGetValues()!!
 *      MyViewModel(viewModelArguments)
 * }
 * ```
 */
inline fun <reified VM : ViewModel> lazyViewModelOrNull(activity: FragmentActivity, noinline creator: () -> VM): Lazy<VM?> {
    return lazy { provideViewModelOrNull(activity, creator) }
}
