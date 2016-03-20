(ns meetdown.view-test
  (:require [cljs.core.async :refer [<! chan]]
            [cljs.test :as t :refer-macros [async deftest is run-tests]]
            [meetdown.view :as v]
            [meetdown.messages :refer [FindEvent]]
            [cljs-http.client :as http])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(deftest test-event-lookup
  (let [c (chan)]
    (v/event-lookup c {:route-params {:id 1234}})
    (async done
           (go
             (let [msg (<! c)]
               (is (= (:id msg) 1234))
               (is (= FindEvent (type msg)))
               (done))))))

;; TODO I have no idea why but this works when run via doo but it doesn't when I run run-tests through
;; the repl in CIDER using (cljs.test/run-tests 'meetdown.view-test)
