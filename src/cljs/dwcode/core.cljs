(ns dwcode.core
  (:require [reagent.core :as reagent :refer [atom]]
            [secretary.core :as secretary :include-macros true :refer [defroute]]
            [goog.events :as events]
            [ajax.core :refer [GET POST]])
  (:import goog.History
           goog.history.EventType))


(defn todo-list [todos]
  [:ul.content
   (for [{:keys [description state]} @todos]
     ^{:key state}
     [:li
      [:p state]
      [:p " - " description]])])

(defn get-todos [todos]
  (GET "/todos"
       {:headers {"Accept" "application/transit+json"}
        :handler #(reset! todos (vec %))}))

(defn send-todo! [fields errors todos]
  (POST "/todo"
        {:headers {"Accept"       "application/transit+json"
                   "x-csrf-token" (.-value (.getElementById js/document "token"))}
         :params @fields
         :handler #(do
                    (reset! errors nil)
                    (swap! todos))
         :error-handler #(do
                          (.log js/console (str %))
                          (reset! errors (get-in % [:response :errors])))}))

(defn errors-component [errors id]
  (when-let [error (id @errors)]
    [:div.alert.alert-danger (clojure.string/join error)]))

(defn todo-form [todos]
  (let [fields (atom {})
        errors (atom nil)]
    (fn []
      [:div.content
       [errors-component errors :server-error]
       [:div.form-group
        [errors-component errors :state]
        [:p "State:"
         [:input.form-control
          {:type      :checkbox
           :name      :state
           :on-change #(swap! fields assoc :name (-> % .-target .-value))
           :value     (:state @fields)}]]
        [errors-component errors :description]
        [:p "Description:"
         [:textarea.form-control
          {:rows      4
           :cols      50
           :name      :description
           :value     (:description @fields)
           :on-change #(swap! fields assoc :description (-> % .-target .-value))}]]
        [:input.btn.btn-primary
         {:type     :submit
          :on-click #(send-todo! fields errors todos)
          :value    "comment"}]]])))

(defn home []
  (let [todos (atom nil)]
    (get-todos todos)
    (fn []
      [:div
       [:div.row
        [:div.span12
         [todo-list todos]]]
       [:div.row
        [:div.span12
         [todo-form todos]]]])))

(defn about []
  [:p "Hi. This is as far as I got. I went over the lumninus web framework and the guestbook application
       I copied it and tried to transform it to function as a todo website. Unfortunately I hit serval
       snags with dependencies and following the tutorial. Initially they guestbook application did
       not work because something changed inthe h2 module. I switched to +sqlite and that fixed 
       the issue. Now it seems the secretary routing module has changed. It's about 4-5 hours now.
       I did not get to the todo functionality. I am submitting this code."])



(defn page [page-component]
  (reagent/render-component
    [page-component]
    (.getElementById js/document "content")))


(defroute home-path "/" [] (page home))
(defroute home-path "/about" [] (page info))
(defroute "*" [] (page not-found))