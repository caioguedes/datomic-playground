(ns playground.tutorial
  (:require
   ;;[datomic.client.api :as d] for com.datomic/local
   [datomic.api :as d]))

;; Local Client - In-Memory
#_(def client-mem (d/client {:server-type :datomic-local
                             :storage-dir :mem ;; in-memory database
                             :system "dev"}))

(defn db-uri [db]
  (str "datomic:sql://" db "?jdbc:postgresql://localhost:5432/datomic?user=datomic&password=datomic"))

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

;; Query all entity ids that contains movie title
(def all-movies-q '[:find ?e ;; Define logic variable
                    ;; Doc Ref: bind the id of each entity that has attribute called :movie/title
                    :where [?e :movie/title]])

;; Query all movie titles
;; Doc Ref: Find all movie titles from any entity that has an attribute :movie/title and assign it to ?movie-title
(def all-titles-q '[:find ?movie-title
                    :where [_ :movie/title ?movie-title]])

(def titles-from-1985 '[:find ?title
                        :where
                        [?e :movie/title ?title] ;; bind
                        [?e :movie/release-year 1985]]) ;; filter
                        ;; ?e join the clauses (and?)

(def all-data-from-1985 '[:find ?title ?year ?genre
                          :where
                          ;; binds
                          [?e :movie/title ?title]
                          [?e :movie/release-year ?year]
                          [?e :movie/genre ?genre]
                          ;; filter
                          [?e :movie/release-year 1985]])

;; Query with Inputs
(def titles-from-year '[:find ?title
                        :in $ ?year
                        :where
                        [?e :movie/title ?title]
                        [?e :movie/release-year ?year]])
                        ;; & refer to database name

;; bind-coll, year = ? or year = ? or ...
(def titles-from-years '[:find ?title
                         :in $ [?year ...]
                         :where
                         [?e :movie/title ?title]
                         [?e :movie/release-year ?year]])

(comment

  (d/q all-movies-q db)
  (d/q all-titles-q db)

  (d/q titles-from-1985 db)
  (d/q '[:find ?title
         :where
         [_ :movie/title ?title]
         [_ :movie/release-year 1985]] db)
  ;; _ = has :moviet/title or :movie/release-year equals 1985?

  (d/q all-data-from-1985 db)

  (d/q titles-from-year db 1985)
  (d/q titles-from-years db [1985 1984])

  ;; Reset
  (do
    (d/delete-database datomic-uri)
    (d/create-database datomic-uri)
    (def conn (d/connect datomic-uri))
    (def db (d/db conn)))
  :hodor)
