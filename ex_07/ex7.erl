%%%-------------------------------------------------------------------
%%% @author hoeni
%%% @copyright (C) 2025, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 07. Dez 2025 12:57
%%%-------------------------------------------------------------------
-module(ex7).
-author("hoeni").

%% API
%% exportiert die "public" Funktionen
%% Syntax: [Funktion/#Argumente]
-export([convert/2]).

convert(Amount, inch) ->
  Amount / 2.54;

convert(Amount, cm) ->
  Amount * 2.54.