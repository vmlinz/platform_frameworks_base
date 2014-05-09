/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.net;

import static com.android.internal.util.Protocol.BASE_CONNECTIVITY_SERVICE;

/**
 * Describes the Internal protocols used to communicate with ConnectivityService.
 * @hide
 */
public class ConnectivityServiceProtocol {

    private static final int BASE = BASE_CONNECTIVITY_SERVICE;

    private ConnectivityServiceProtocol() {}

    /**
     * This is a contract between ConnectivityService and various bearers.
     * A NetworkFactory is an abstract entity that creates NetworkAgent objects.
     * The bearers register with ConnectivityService using
     * ConnectivityManager.registerNetworkFactory, where they pass in a Messenger
     * to be used to deliver the following Messages.
     */
    public static class NetworkFactoryProtocol {
        private NetworkFactoryProtocol() {}
        /**
         * Pass a network request to the bearer.  If the bearer believes it can
         * satisfy the request it should connect to the network and create a
         * NetworkAgent.  Once the NetworkAgent is fully functional it will
         * register itself with ConnectivityService using registerNetworkAgent.
         * If the bearer cannot immediately satisfy the request (no network,
         * user disabled the radio, lower-scored network) it should remember
         * any NetworkRequests it may be able to satisfy in the future.  It may
         * disregard any that it will never be able to service, for example
         * those requiring a different bearer.
         * msg.obj = NetworkRequest
         * msg.arg1 = score - the score of the any network currently satisfying this
         *            request.  If this bearer knows in advance it cannot
         *            exceed this score it should not try to connect, holding the request
         *            for the future.
         *            Note that subsequent events may give a different (lower
         *            or higher) score for this request, transmitted to each
         *            NetworkFactory through additional CMD_REQUEST_NETWORK msgs
         *            with the same NetworkRequest but an updated score.
         *            Also, network conditions may change for this bearer
         *            allowing for a better score in the future.
         */
        public static final int CMD_REQUEST_NETWORK = BASE;

        /**
         * Cancel a network request
         * msg.obj = NetworkRequest
         */
        public static final int CMD_CANCEL_REQUEST = BASE + 1;
    }
}
