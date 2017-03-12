(ns dwcode.app
  (:require [dwcode.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
