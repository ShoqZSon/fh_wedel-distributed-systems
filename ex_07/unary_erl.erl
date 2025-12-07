%%%-------------------------------------------------------------------
%%% @author hoeni
%%% @copyright (C) 2025, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 07. Dez 2025 13:10
%%%-------------------------------------------------------------------
-module(unary_erl).
-export([maxitem/1]).

%% öffentliche Funktion
maxitem([]) ->
  0;
maxitem([F | R]) ->
  maxitem(R, F).

%% Binäre Hilfsfunktion maxitem/2
%% 1. Case: Ist die Liste leer bleibt das Max gleich
maxitem([], Max) ->
  io:format("Ende der Liste, Max: ~p~n", [Max]),
  Max;
%% 2. Case: Das erste Element wird mit Max verglichen und wenn F > Max wird mit 'R' weitergemacht
maxitem([F | R], Max) when F > Max ->
  io:format("Größeres Element gefunden: ~p > ~p~n", [F,Max]),
  maxitem(R, F);
%% 3. Case: Das erste Element ist kleiner als Max und somit wird mit dem Rest 'R' weitergemacht
maxitem([_ | R], Max) ->
  io:format("Kein größeres Element gefunden. Max (~p) bleibt gleich.~n", [Max]),
  maxitem(R, Max).

