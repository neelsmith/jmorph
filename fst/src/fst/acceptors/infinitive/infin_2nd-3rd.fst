% Extend stem with sigma:
% Extend stem of infinitive with sigma in 2nd and 3rd principal parts
%
#include "@workdir@symbols.fst"

#=ltr# = a-z
ALPHABET = [#letter#] [#morphtag#] [#urn#] [\:] [<#>] [#stemtype#] [#extratag#] [#persistacc#]
$2nd_3rd_pp$ = {[#=ltr#]}:{[#=ltr#]s} ^-> ([#urn#][#stemchars#]+ __ <verb><w_regular>[#extratag#]*[\:]+<infin>[#letter#]+[<aor><fut><futpft>][<act><mid>][#persistacc#])

$2nd_3rd_pp$
