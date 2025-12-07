%%%-------------------------------------------------------------------
%%% @author hoeni
%%% @copyright (C) 2025, <COMPANY>
%%% @doc
%%%
%%% @end
%%% Created : 07. Dez 2025 14:41
%%%-------------------------------------------------------------------
-module(derivative).
-author("hoeni").

%% API
-export([diff/3]).

%% Erwartet über die Erl Shell eine Funktion mit Syntax:
%% F = Fun(X) -> a*X*..*X +- b*X*..*X +- ... +- Z end. | (allg. Polynom)
%% Beispiel:
%% F = Fun(X) -> 2*X*X*X - 12*X + 3
%% diff(F,3,1.0e-10) => ~42
diff(Fun,X,H) ->
  io:format("Fun: ~p, X: ~p, H: ~p~n", [Fun,X,H]),
  (Fun(X + H) - Fun(X - H)) / (2 * H).
