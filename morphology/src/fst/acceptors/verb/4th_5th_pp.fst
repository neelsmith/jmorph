% 4th_5th_pp.sft
%
#include "@workdir@symbols.fst"

% Tense/voice combinations for fourth-fifth principal parts
%
% For regular omega verbs,  reduplicate in all voices.
% In perfect active, extend stem with kappa.
% In pluperfect, augment is taken care of separately?
%


#4th_5th_tense# = <pft><plupft>


% Add reduplication on 4th and 5th parts:
#=ltr# = a-z
ALPHABET = [#letter#] [#morphtag#] [#urn#] [\:] [<#>] [#stemtype#] [#extratag#]


%%%%% 4th and 5th principal parts %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Extend stem with kappa in 4th (active voice):
#=ltr# = a-z
ALPHABET = [#letter#] [#morphtag#] [#urn#] [\:] [<#>] [#stemtype#] [#extratag#] [#vowelquant#]
$kappa$ = {[#=ltr#]}:{[#=ltr#]k} ^-> ([#urn#]+[#stemchars#]+ __ [#vowelquant#]*<verb><w_regular>[#extratag#]*[\:]+<w_regular>[#urn#][#letter#]*[#person#][#number#][#4th_5th_tense#][#mood#]<act>)

% Works with both 4-5 parts and passes through 1st part:
% $kappa$

% Reduplication on both 4th and 5th parts:
ALPHABET = [#letter#] [#morphtag#] [#urn#] [\:] [<#>] [#stemtype#] [#extratag#] [#vowelquant#]
$redupe$ = {[#=ltr#]}:{[#=ltr#]e[#=ltr#]}  ^-> ([#urn#]+<#>? __ [#stemchars#]+<verb><w_regular>[#extratag#]*[\:]+<w_regular>[#urn#][#letter#]*[#person#][#number#][#4th_5th_tense#][#mood#][#voice#])


% Works with both 4-5 parts and passes through 1st part:
% $redupe$



$4th_5th_pp$ =   $kappa$ || $redupe$
$4th_5th_pp$


%<coretests.n64316_0><lexent.n64316><#>lu<verb><w_regular>::<w_regular><w_indicative.1>w<1st><sg><fut><indic><act>
