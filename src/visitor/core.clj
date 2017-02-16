(ns visitor.core
  (:require [clojure.zip :as z]
            [zip.visit :as zv])
  (:gen-class))

(defn branch? [node]
  (println "branch?:" node)
  (map? node))

(defn add-parent-path-to-children
  [parent children]
  (map #(assoc % :v/parent parent) children))

(defn children [node]
  (println "children:" node)
  (add-parent-path-to-children node (:children node)))

(defn make-node [node children]
  (println "make-node:" node " with children:" children)
  (if node
    (assoc node :children children)))

(zv/defvisitor visited :pre [n s]
  (println :pre "node:" n " state:" s)
  {:node n
   :state s})

(defn test-visit
  [root]
  (let [zipper (z/zipper branch? children make-node root)]
    (zv/visit zipper nil [visited])))

(def test1 {:name "first" :children [{:name "child1"
                                      :children [{:name "child1's children 1"} {:name "child1's chidlren 2"}]}
                                     {:name "child2"}]})

