package com.example.fhananfarhan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {

    private static final String ARG_RATING = "rating";
    private float rating;
    private LottieAnimationView lottieAnimationView;
    private Button sendButton;

    public static CommentFragment newInstance(float rating) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_RATING, rating);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rating = getArguments().getFloat(ARG_RATING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        TextView ratingDisplay = view.findViewById(R.id.ratingDisplay);
        EditText commentInput = view.findViewById(R.id.commentInput);
        sendButton = view.findViewById(R.id.sendButton);
        lottieAnimationView = view.findViewById(R.id.animationView);

        ratingDisplay.setText("Rating: " + rating);

        sendButton.setOnClickListener(v -> {
            Animation bounce = AnimationUtils.loadAnimation(getContext(), R.anim.button_bounce);
            sendButton.startAnimation(bounce);

            String comment = commentInput.getText().toString().trim();

            if (TextUtils.isEmpty(comment)) {
                Toast.makeText(getContext(), "Please add a comment", Toast.LENGTH_SHORT).show();
            } else {
                sendButton.setEnabled(false);
                sendButton.setText("Submitting...");
                sendCommentToServer(comment, rating);
            }
        });

        return view;
    }

    private void sendCommentToServer(String comment, float rating) {
        CommentApi apiService = ApiClient.getRetrofitInstance().create(CommentApi.class);
        String uid = "36"; // Replace with dynamic UID if available
        ReviewRequest request = new ReviewRequest((int) rating, comment, uid);

        Call<ApiResponse> call = apiService.sendReview(request);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    // Show Lottie Animation
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();

                    // Delay for 2 seconds and then navigate
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(getActivity(), CommentListActivity.class));
                        requireActivity().finish();
                    }, 2000);

                } else {
                    Toast.makeText(getContext(), "Post failed: Invalid response", Toast.LENGTH_LONG).show();
                    sendButton.setEnabled(true);
                    sendButton.setText("Submit");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                sendButton.setEnabled(true);
                sendButton.setText("Submit");
            }
        });
    }
}
