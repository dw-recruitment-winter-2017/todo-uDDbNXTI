(ns dwcode.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[dwcode started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[dwcode has shut down successfully]=-"))
   :middleware identity})
