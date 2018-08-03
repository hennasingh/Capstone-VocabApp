package com.girl.coder.vocabictionary.dependencyinjection.components;

import com.girl.coder.vocabictionary.dependencyinjection.ApplicationComponent;
import com.girl.coder.vocabictionary.dependencyinjection.RoomModule;
import com.girl.coder.vocabictionary.dependencyinjection.modules.OxfordModule;
import com.girl.coder.vocabictionary.dependencyinjection.scope.UserScope;
import com.girl.coder.vocabictionary.network.repository.WebRepository;
import com.girl.coder.vocabictionary.view.fragments.DictionaryFragment;
import com.girl.coder.vocabictionary.view.fragments.ThesaurusFragment;
import com.girl.coder.vocabictionary.view.fragments.WordListFragment;
import com.girl.coder.vocabictionary.widget.ListRemoteViewsFactory;

import dagger.Component;

/*
 we can define a child component
 */
@UserScope // @Singleton will not work so previously created scope
@Component(dependencies = ApplicationComponent.class, modules = {OxfordModule.class, RoomModule.class})
public interface OxfordComponent {

    WebRepository provideWebRepo();

    void inject(DictionaryFragment dictFragment);

    void inject(WordListFragment wordFragment);

    void inject(ThesaurusFragment thesFragment);

    void inject(ListRemoteViewsFactory widgetService);


}
