% Facts about music
genre(jazz).
genre(pop).
genre(rock).
genre(classical).
genre(electronic).
genre(blues).
genre(country).

mood(upbeat).
mood(relaxing).
mood(energetic).
mood(mellow).
mood(happy).

tempo(fast).
tempo(moderate).
tempo(slow).

instrument(piano).
instrument(guitar).
instrument(drums).
instrument(saxophone).
instrument(violin).
instrument(electronic_beats).

artist(john_coltrane).
artist(beatles).
artist(led_zeppelin).
artist(beethoven).
artist(daft_punk).
artist(buddy_guy).
artist(johnny_cash).

% Rules for music recommendation
recommended_music(X, Y, Z, A) :-
    genre(X),
    mood(Y),
    tempo(Z),
    instrument(A),
    likes_genre_mood_tempo_instrument(Y, X, Z, A).

likes_genre_mood_tempo_instrument(upbeat, pop, fast, guitar).
likes_genre_mood_tempo_instrument(relaxing, jazz, slow, saxophone).
likes_genre_mood_tempo_instrument(energetic, rock, fast, drums).
likes_genre_mood_tempo_instrument(mellow, electronic, moderate, electronic_beats).
likes_genre_mood_tempo_instrument(happy, country, moderate, guitar).

% Additional rules for artist preferences
likes_artist(jazz, john_coltrane).
likes_artist(pop, beatles).
likes_artist(rock, led_zeppelin).
likes_artist(classical, beethoven).
likes_artist(electronic, daft_punk).
likes_artist(blues, buddy_guy).
likes_artist(country, johnny_cash).

recommended_music_by_artist(Genre, Artist) :-
    genre(Genre),
    likes_artist(Genre, Artist).

% Example usage:
% recommended_music(Genre, Mood, Tempo, Instrument).
% recommended_music_by_artist(Genre, Artist).
% For example: recommended_music(pop, upbeat, fast, guitar).
%              recommended_music_by_artist(jazz, john_coltrane).
