package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private List<Song> songList;
    private OnSongClickListener listener;

    public interface OnSongClickListener {
        void onPlayClick(Song song);
        void onPauseClick();
    }

    public SongsAdapter(List<Song> songList, OnSongClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongViewHolder holder, int position) {
        final Song song = songList.get(position);
        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());

        holder.playButton.setVisibility(View.VISIBLE);
        holder.pauseButton.setVisibility(View.GONE);

        holder.playButton.setOnClickListener(v -> {
            listener.onPlayClick(song);
            holder.playButton.setVisibility(View.GONE);
            holder.pauseButton.setVisibility(View.VISIBLE);
        });

        holder.pauseButton.setOnClickListener(v -> {
            listener.onPauseClick();
            holder.pauseButton.setVisibility(View.GONE);
            holder.playButton.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView title, artist;
        ImageButton playButton, pauseButton;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            artist = itemView.findViewById(R.id.textViewArtist);
            playButton = itemView.findViewById(R.id.buttonPlay);
            pauseButton = itemView.findViewById(R.id.buttonPause);
        }
    }
}
