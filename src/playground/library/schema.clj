(ns playground.library.schema)

(def library-schema
  [{:db/ident :member/name
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "A member's name"}
   {:db/ident :member/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/unique :db.unique/identity
    :db/doc "A member's email address"}
   {:db/ident :book/isbn
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
    :db/doc "A book's publish year"}
   {:db/ident :loan/date
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

