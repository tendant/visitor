(ns visitor.core
  (:require [clojure.zip :as z]
            [zip.visit :as zv])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn branch? [node]
  (println "branch?:" node)
  (map? node))

(defn children [node]
  (println "children:" node)
  (:children node))

(defn add-parent-to-children
  [parent children]
  (map #(assoc % :v/parent parent) children))

(defn make-node [node children]
  (println "make-node:" node " with children:" children)
  (assoc node :children (add-parent-to-children node children)))

(def test1 {:name "first" :children [{:name "child1"
                                      :children {:name "child1's children" :children [{:name "child1's children 1"} {:name "child1's chidlren 2"}]}}
                                     {:name "child2"}]})

(zv/defvisitor visited :pre [n s]
  (println :pre "node:" n " state:" s)
  {:node n
   :state (assoc s :parent n)})

(defn test-visit
  [root]
  (let [zipper (z/zipper branch? children make-node root)]
    (zv/visit zipper nil [visited])))
