(ns playground.library-management
  (:require [datomic.api :as d]))

;; Define Schema for a Library Managment System
(def members-schema
  [{:db/ident :member/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A member's name"}

   {:db/ident :member/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique :db.unique/identity
    :db/doc "A member's email address"}])

(def books-schema
  [{:db/ident :book/isbn
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db/unique :db.unique/identity
    :db/doc "A book's ISBN-13"}

   {:db/ident :book/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A book's title"}

   {:db/ident :book/author
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A book's author"}

   {:db/ident :book/publish-year
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc "A book's publish year"}])

(def loans-schema
  [{:db/ident :loan/date  ;; should be drop in order of entity t?
    :db/valueType :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc "A loan's date"}

   {:db/ident :loan/due-date
    :db/valueType :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc "A loan's due date"}

   {:db/ident :loan/member
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "A loan's member"}

   {:db/ident :loan/books
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many
    :db/doc "The loan's books"}])

(def library-schema
  (concat
   members-schema
   books-schema
   loans-schema))

(def library-samples
  [{:book/isbn 9781680502466
    :book/title "Programming Clojure (The Pragmatic Programmers)"
    :book/author "Miller, Alex - Halloway, Stuart - Bedra, Aaron"
    :book/publish-year 2018}

   {:book/isbn 9781680503005
    :book/title "Getting Clojure: Build Your Functional Skills One Idea at a Time"
    :book/author "Olsen, Russ"
    :book/publish-year 2018}

   {:book/isbn 9781593275914
    :book/title "Clojure for the Brave and True: Learn the Ultimate Language and Become a Better Programmer"
    :book/author "Daniel Higginbotham"
    :book/publish-year 2015}

   {:member/name "Member 1"
    :member/email "member1@example.com"}

   {:member/name "Member 2"
    :member/email "member2@example.com"}])

;; Queries
;; * Loans that happend in the this month
;; * Member with most number of loans this year
;; * Members that loaned an specific book
;; * Spacific books is availiable to be loand? If not when?

(comment
  (def uri (playground.tutorial/db-uri "library"))
  (d/delete-database uri)
  (d/create-database uri)
  (def conn (d/connect (playground.tutorial/db-uri "library")))

  ;; Initialize Database

  @(d/transact conn library-schema)
  @(d/transact conn library-samples)

  (def db (d/db conn))


  (def loan-date (java.time.Instant/now))
  (def loan [{;;:loan/member {:member/email "member2@example.com"}
              :loan/date loan-date
             ;;:loan/due-date (.plus loan-date 20 java.time.temporal.ChronoUnit/DAYS)
             ;;:loan/books [{:book/isbn 9781593275914}]
              }])

  @(d/transact conn load)


  (doto (java.time.Instant/now) (.plus 10 java.time.temporal.ChronoUnit/DAYS))

  (d/q '[:find ?isbn ?title
         :where
         [?e :book/title ?title]
         [?e :book/isbn ?isbn]] db)

  (d/q '[:find (pull ?e [*])
         :where [?e :book/isbn]] db)

  :hodor)