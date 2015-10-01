%infinitive.fst
% Acceptor for infinitive pattern:
% tense, <inf>, voice


#include "@workdir@symbols.fst"

$infinacceptor$ = [#urn#][#stemchars#]+<verb>[#verbclass#][#extratag#]*\:\:<infin>[#stemchars#]+[#tense#][#voice#][#persistacc#]


$infinacceptor$

% Example:
% <n64316><#>lu<verb><w_regular>::<infin>ein<pres><act><penacc>