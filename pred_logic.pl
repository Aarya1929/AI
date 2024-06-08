female(hirabai).
female(kalavati).
female(yogita).
female(sheetal).
female(priyanka).
female(jyoti).
female(vidya).
female(kaveri).
female(manisha).
female(aarya).
female(shourya).
female(preetisha).
female(ovi).

male(sahebrao1).
male(balasaheb).
male(sunil).
male(pravin).
male(sahebrao2).
male(dnyaneshwar).
male(abhijeet).
male(sandeep).
male(gopinath).
male(aarav).
male(kshitij).
male(shreyash).
male(veer).
male(yajat).
male(rajveer).
male(neil).

parent(sahebrao1,jyoti).
parent(sahebrao1,vidya).
parent(sahebrao1,sunil).
parent(sahebrao1,pravin).
parent(balasaheb,yogita).
parent(balasaheb,sheetal).
parent(balasaheb,priyanka).
parent(balasaheb,abhijeet).
parent(sahebrao2,shreyash).
parent(dnyaneshwar,kshitij).
parent(sunil,aarya).
parent(sunil,aarav).
parent(pravin,shourya).
parent(pravin,veer).
parent(sandeep,preetisha).
parent(sandeep,ovi).
parent(gopinath,yajat).
parent(abhijeet,rajveer).
parent(abhijeet,neil).

parent(hirabai,jyoti).
parent(hirabai,vidya).
parent(hirabai,sunil).
parent(hirabai,pravin).
parent(kalavati,yogita).
parent(kalavati,sheetal).
parent(kalavati,priyanka).
parent(kalavati,abhijeet).
parent(jyoti,shreyash).
parent(vidya,kshitij).
parent(yogita,aarya).
parent(yogita,aarav).
parent(manisha,shourya).
parent(manisha,veer).
parent(sheetal,preetisha).
parent(sheetal,ovi).
parent(priyanka,yajat).
parent(kaveri,rajveer).
parent(kaveri,neil).

married(sahebrao1, hirabai).
married(balasaheb, kalavati).
married(sahebrao2,jyoti).
married(dnyaneshwar,vidya).
married(sunil,yogita).
married(pravin,manisha).
married(sandeep,sheetal).
married(gopinath,priyanka).
married(abhijeet,kaveri).

paternal_grandparent(X,Y):- parent(X,Z),parent(Z,Y).
maternal_grandparent(X,Y) :-parent(Z,Y),parent(X,Z).

mother(X,Y):- parent(X,Y),female(X).
father(X,Y):- parent(X,Y),male(X).
grandson(X,Y):-parent(X,Z),parent(Z,Y),male(Y).
granddaughter(X,Y):-parent(X,Z),parent(Z,Y),female(Y).

daughter_in_law(Y,Z):-parent(Z,X),male(X),married(Y,X),female(Y).
son_in_law(X,Z):-parent(Z,Y),male(Z),married(Y,X),male(Y).

sibling(X,Y):- parent(Z,X),parent(Z,Y),X\==Y.
sister(X,Y):- sibling(X,Y),female(X).
brother(X,Y):- sibling(X,Y),male(X).
cousin(L,M):- parent(Y,L),parent(Z,M),sibling(Y,Z).
uncle(X,Y):- parent(Z,Y),sibling(X,Z),male(X).
aunt(X,Y):- parent(Z,Y),sibling(X,Z),female(X).
aunt(X,Y):- married(Z,X),uncle(Z,Y),female(X).
niece(X,Y):- aunt(X,Y),female(Y).
niece(X,Y):- uncle(X,Y),female(Y).
nephew(X,Y):- uncle(X,Y),male(X).
nephew(X,Y):- aunt(X,Y),male(X).
