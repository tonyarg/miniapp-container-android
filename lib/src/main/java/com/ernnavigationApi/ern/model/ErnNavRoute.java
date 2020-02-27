/*
* Copyright 2017 WalmartLabs
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ernnavigationApi.ern.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

import com.walmartlabs.electrode.reactnative.bridge.Bridgeable;

import static com.walmartlabs.electrode.reactnative.bridge.util.BridgeArguments.*;

public class ErnNavRoute implements Parcelable, Bridgeable {

    private String path;
    private String jsonPayload;
    private NavigationBar navigationBar;
    private Boolean overlay;
    private Boolean refresh;

    private ErnNavRoute() {}

    private ErnNavRoute(Builder builder) {
        this.path = builder.path;
        this.jsonPayload = builder.jsonPayload;
        this.navigationBar = builder.navigationBar;
        this.overlay = builder.overlay;
        this.refresh = builder.refresh;
    }

    private ErnNavRoute(Parcel in) {
        this(in.readBundle());
    }

    public ErnNavRoute(@NonNull Bundle bundle) {
        if(!bundle.containsKey("path")){
            throw new IllegalArgumentException("path property is required");
        }

        this.path = bundle.getString("path");
        this.jsonPayload = bundle.getString("jsonPayload");
        this.navigationBar = bundle.containsKey("navigationBar") ? new NavigationBar(bundle.getBundle("navigationBar")) : null;
        this.overlay = bundle.containsKey("overlay") ? bundle.getBoolean("overlay") : null;
        this.refresh = bundle.containsKey("refresh") ? bundle.getBoolean("refresh") : null;
    }

    public static final Creator<ErnNavRoute> CREATOR = new Creator<ErnNavRoute>() {
        @Override
        public ErnNavRoute createFromParcel(Parcel in) {
            return new ErnNavRoute(in);
        }

        @Override
        public ErnNavRoute[] newArray(int size) {
            return new ErnNavRoute[size];
        }
    };

    /**
    * Path of the Route. Mostly the name of the container(defined by the native app) or the miniapp name. The content of the path is mainly determined by the native implemenation of the API
    *
    * @return String
    */
    @NonNull
    public String getPath() {
        return path;
    }

    /**
    * Optional Payload (respresented as JSON String) needed by the screen you are trying to navigate to.
    *
    * @return String
    */
    @Nullable
    public String getJsonPayload() {
        return jsonPayload;
    }

    @Nullable
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    /**
    * If set to true, the view component would be displayed over a transparent theme. Default value is false.
    *
    * @return Boolean
    */
    @Nullable
    public Boolean getOverlay() {
        return overlay;
    }

    /**
    * Set to true if you would like to re-render a component while navigating back to it.
    *
    * @return Boolean
    */
    @Nullable
    public Boolean getRefresh() {
        return refresh;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(toBundle());
    }

    @NonNull
    @Override
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("path", this.path);
        if(jsonPayload != null) {
            bundle.putString("jsonPayload", this.jsonPayload );
        }
        if(this.navigationBar != null) {
            bundle.putBundle("navigationBar", this.navigationBar.toBundle());
        }
        if(this.overlay != null) {
            bundle.putBoolean("overlay", this.overlay);
        }
        if(this.refresh != null) {
            bundle.putBoolean("refresh", this.refresh);
        }
        return bundle;
    }

    @Override
    public String toString() {
        return "{"
        + "path:" + (path != null ? "\"" + path + "\"" : null)+ ","
        + "jsonPayload:" + (jsonPayload != null ? "\"" + jsonPayload + "\"" : null)+ ","
        + "navigationBar:" + (navigationBar != null ? navigationBar.toString() : null)+ ","
        + "overlay:" + overlay+ ","
        + "refresh:" + refresh
        + "}";
    }

    public static class Builder {
        private final String path;
        private String jsonPayload;
        private NavigationBar navigationBar;
        private Boolean overlay;
        private Boolean refresh;

        public Builder(@NonNull String path) {
            this.path = path;
        }

        @NonNull
        public Builder jsonPayload(@Nullable String jsonPayload) {
            this.jsonPayload = jsonPayload;
            return this;
        }
        @NonNull
        public Builder navigationBar(@Nullable NavigationBar navigationBar) {
            this.navigationBar = navigationBar;
            return this;
        }
        @NonNull
        public Builder overlay(@Nullable Boolean overlay) {
            this.overlay = overlay;
            return this;
        }
        @NonNull
        public Builder refresh(@Nullable Boolean refresh) {
            this.refresh = refresh;
            return this;
        }

        @NonNull
        public ErnNavRoute build() {
            return new ErnNavRoute(this);
        }
    }
}
