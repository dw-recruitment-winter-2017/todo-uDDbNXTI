(ns dwcode.routes.home
  (:require [dwcode.layout :as layout]
            [dwcode.db.core :as db]
            [bouncer.core :as b]
            [bouncer.validators :as v]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render "index.html"))

(defn validate-todo [params]
  (first
   (b/validate
    params
    :description [v/required [v/min-count 10]])))

(defn create-todo! [{:keys [params]}]
  (if-let [errors (validate-todo params)]
    (response/bad-request {:errors errors})
    (try
      (db/create-todo! params)
      (response/ok {:status :ok})
      (catch Exception e
        (response/internal-server-error
{:errors {:server-error ["Failed to save todo!"]}})))))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/todos" [] (response/ok (db/get-todos)))
  (POST "/todo" req (create-todo! req))
  (GET "/about" [] (about-page)))
  
