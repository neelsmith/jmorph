% indeclinable.fst
% Acceptor for indeclinable literals:


#include "@workdir@symbols.fst"



$indecl$ = <u>[#urnchar#]+ [#period#] [#urnchar#]+</u><u>lexent[#period#][#urnchar#]*</u>[#stemchars#]+ <indecl> $separator$+ <indecl>  <u>indeclinfl\.0</u>

%
%Strip out string values from URNs:
%
$squashurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<indecl> $separator$+ <indecl> <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>

%
% Strip out all tags
%
#analysissymbol# = #urn# #editorial# #morphtag# #stemtype# #separator# #urntag#
#surfacesymbol# = #character#
ALPHABET = [#surfacesymbol#] [#analysissymbol#]:<>
$striptag$ = .*


$indeclacceptor$ =  $indecl$ || $squashurn$ || $striptag$
$indeclacceptor$