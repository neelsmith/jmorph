% acceptor.fst
%
% Ensure that input matches one of the eight defined analytical pattens.
% To map to surface form, first squash URN values, then remove tags.
%
#include "@workdir@symbols.fst"

%%  Acceptors for each of the 8 analytical patterns
%
% Noun acceptor:
$=nounclass$ = [#nounclass#]
$squashnounurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<noun>$=gender$ $=nounclass$  [#persistacc#]  $separator$+ $=nounclass$  <noun> [#stemchars#]* $=gender$ $case$ $number$ <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>

% Adjective acceptor:
$=adjclass$ = [#adjectiveclass#]

$squashadjurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<adj>$=adjclass$  [#persistacc#]  $separator$+ $=adjclass$ <adj> [#stemchars#]* $gender$ $case$ $number$ [#degree#] <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>


% Conjugated verb acceptor:
$=verbclass$ = [#verbclass#]
$squashverburn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<verb>$=verbclass$  $separator$+$=verbclass$ <verb>[#stemchars#]* [#person#] [#number#] [#tense#] [#mood#] [#voice#]<u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>


% Verb: infinitive acceptor:
$squashinfinurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<verb>$=verbclass$  $separator$+$=verbclass$  <infin> [#stemchars#]*  [#tense#] [#voice#]<u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>

% Verb: participle acceptor:
$squashptcplurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<verb>$=verbclass$  $separator$+$=verbclass$ <ptcpl>[#stemchars#]*  [#gender#][#case#][#number#][#tense#]  [#voice#]<u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>

% Verb: verbal adjective acceptor
$squashvadjurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u>[#stemchars#]+<verb>$=verbclass$  $separator$+$=verbclass$ <vadj>[#stemchars#]*  [#gender#][#case#][#number#]<vadj1><u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>


% Indeclinable form acceptor:
$=indeclclass$ = [#indeclclass#]
$squashindeclurn$ = <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u> <u>{lexent}:<>\.:<>[#urnchar#]:<>+</u> [#stemchars#]+  $=indeclclass$  $separator$+  $=indeclclass$ <indecl> <u>[#urnchar#]:<>+\.:<>[#urnchar#]:<>+</u>



%% Union of all acceptor squashers:
$acceptor$ = $squashverburn$ | $squashnounurn$ | $squashinfinurn$ | $squashptcplurn$ | $squashvadjurn$  | $squashindeclurn$


%% Put all symbols in 2 categories:  pass through
%% surface symbols, squash analytical symbols.
#analysissymbol# = #editorial# #urntag# <noun><verb><indecl><ptcpl><infin><vadj><adj><adv> #morphtag# #stemtype#  #separator# #accent#
#surfacesymbol# = #letter# #diacritic#  #breathing#
ALPHABET = [#surfacesymbol#] [#analysissymbol#]:<>
$stripsym$ = .+


%% The canonical pipeline: (morph data) -> acceptor -> parser/stripper
$acceptor$ || $stripsym$
