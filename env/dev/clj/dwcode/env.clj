(ns dwcode.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [dwcode.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[dwcode started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[dwcode has shut down successfully]=-"))
   :middleware wrap-dev})
