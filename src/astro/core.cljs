(ns astro.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))


(defn setup []
  (q/frame-rate 30)
  (q/color-mode :rgb)                                       ; change me to :hsb
  {:dx    0
   :dy    0
   :color 0
   :angle 0})


(defn update-state [state]
  (let [new-dx (* 25 (if (and (:key-a state) (:key-d state))
                       0
                       (if (:key-a state)
                         -1
                         (if (:key-d state)
                           +1
                           0))))]
    {:color (mod (+ (:color state) 0.7) 255)
     :angle (+ (:angle state) 0.1)
     :dx    (+ (:dx state) new-dx)}))


(defn draw-state [state]
  ;(q/background 255 255 255)                                ; try me
  ;(q/background 246 245 236)                                ; try me
  (q/fill (:color state) 255 255)
  (let [angle (:angle state)
        dx (:dx state)
        dy (:dy state)
        x (+ (* 150 (q/cos angle)) dx)
        y (+ (* 150 (q/sin angle)) dy)]
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
                        (q/ellipse x y 100 100))))


(defn key-pressed [state event]
  (let [new-state (if (= (:key event) :a)
                    (assoc state :key-a true)
                    state)]
    (if (= (:key event) :d)
      (assoc new-state :key-d true)
      new-state)))


(defn key-released [state event]
  (let [new-state (if (= (:key event) :a)
                    (assoc state :key-a false)
                    state)]
    (if (= (:key event) :d)
      (assoc new-state :key-d false)
      new-state)))


(defn ^:export run-sketch []
  (q/defsketch astro-1999
               :title "astro 1999"
               :size [500 500]
               :setup setup
               :update update-state
               :draw draw-state
               :key-pressed key-pressed
               :key-released key-released
               :middleware [m/fun-mode]))
