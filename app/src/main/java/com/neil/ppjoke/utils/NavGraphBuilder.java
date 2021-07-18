package com.neil.ppjoke.utils;

import android.content.ComponentName;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

import com.neil.ppjoke.FixFragmentNavigator;
import com.neil.ppjoke.model.Destination;

import java.util.HashMap;

/**
 * @author zhongnan
 * @time 2021/7/14 20:40
 *
 */
public class NavGraphBuilder {

    public static void build(NavController controller, FragmentActivity activity, int containerId) {
        NavigatorProvider provider = controller.getNavigatorProvider();

        FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(activity, activity.getSupportFragmentManager(), containerId);
        provider.addNavigator(fragmentNavigator);
        //FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));
        HashMap<String, Destination> destConfig = AppConfig.getsDestConfig();

        for(Destination value : destConfig.values()) {
            if(value.getIsFragment()) {
                FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setClassName(value.getClazzName());
                destination.setId(value.getId());
                destination.addDeepLink(value.getPageUrl());
                navGraph.addDestination(destination);
            } else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setComponentName(new ComponentName(AppGlobals.getApplication().getPackageName(), value.getClazzName()));
                destination.setId(value.getId());
                destination.addDeepLink(value.getPageUrl());
                navGraph.addDestination(destination);
            }

            if(value.getAsStarter()) {
                navGraph.setStartDestination(value.getId());
            }
        }

        controller.setGraph(navGraph);
    }
}
