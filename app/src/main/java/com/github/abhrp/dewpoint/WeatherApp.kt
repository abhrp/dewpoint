package com.github.abhrp.dewpoint

import com.github.abhrp.dewpoint.di.component.DaggerApplicationComponent
import dagger.android.support.DaggerApplication

/**
 * Icons
 * launcher <div>Icons made by <a href="https://www.flaticon.com/authors/iconnice" title="Iconnice">Iconnice</a> from <a href="https://www.flaticon.com/" 		    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
 * fahrenheit <div>Icons made by <a href="https://www.flaticon.com/<?=_('authors').'/'?>freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" 		    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
 * up <div>Icons made by <a href="https://www.flaticon.com/<?=_('authors').'/'?>phatplus" title="phatplus">phatplus</a> from <a href="https://www.flaticon.com/" 		    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
 * down <div>Icons made by <a href="https://www.flaticon.com/<?=_('authors').'/'?>phatplus" title="phatplus">phatplus</a> from <a href="https://www.flaticon.com/" 		    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 		    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
 */
class WeatherApp : DaggerApplication() {

    private val applicationInjector = DaggerApplicationComponent.builder().application(this).build()

    override fun applicationInjector() = applicationInjector

}