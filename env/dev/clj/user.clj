(ns user
  (:require [mount.core :as mount]
            [dwcode.figwheel :refer [start-fw stop-fw cljs]]
            dwcode.core))

(defn start []
  (mount/start-without #'dwcode.core/http-server
                       #'dwcode.core/repl-server))

(defn stop []
  (mount/stop-except #'dwcode.core/http-server
                     #'dwcode.core/repl-server))

(defn restart []
  (stop)
  (start))


