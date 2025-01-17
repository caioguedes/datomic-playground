(ns playground.tutorial
  (:require
   ;;[datomic.client.api :as d] for com.datomic/local
   [datomic.api :as d]))

;; Local Client - In-Memory
#_(def client-mem (d/client {:server-type :datomic-local
                             :storage-dir :mem ;; in-memory database
                             :system "dev"}))

(def datomic-uri "datomic:sql://movies?jdbc:postgresql://localhost:5432/datomic?user=datomic&password=datomic")

;; Creating Database
(d/create-database datomic-uri)

;; Createing Connection
(def conn (d/connect datomic-uri))

;; Defning Schema
(def movie-schema [{:db/ident :movie/title
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The title of the movie"}

                   {:db/ident :movie/genre
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The genre of the movie"}

                   {:db/ident :movie/release-year
                    :db/valueType :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc "The year the movie was released in theaters"}])

;; Transacting Schema
@(d/transact conn movie-schema)


#_@(d/transact conn [{:db/ident :car/brand
                      :db/cardinality :db.cardinality/one
                      :db/valueType :db.type/string}])

;; @(d/transact conn [{:car/brand "Ford"}])

;; Transacting Data
(def first-movies [{:movie/title "The Goonies"
                    :movie/genre "action/adventure"
                    :movie/release-year 1985}

                   {:movie/title "Commando"
                    :movie/genre "thriller/action"
                    :movie/release-year 1985}

                   {:movie/title "Repo Man"
                    :movie/genre "punk dystopia"
                    :movie/release-year 1984}])

@(d/transact conn first-movies)

;; @(d/transact conn [{:car/brand "Ford"}]) thre is no schema for :car/brand
;; :db.error/not-an-entity Unable to resolve entity: :car/brand

;; Query
(def db (d/db conn))

(comment
  (d/list-databases datomic-uri {})
  (d/db-stats db)
  (d/delete-database datomic-uri)

  (d/q '[:find ?doc ; ?alias 
         :where [_ :db/doc ?doc]] db)

  :hodor)
