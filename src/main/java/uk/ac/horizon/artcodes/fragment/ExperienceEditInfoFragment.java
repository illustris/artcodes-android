package uk.ac.horizon.artcodes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import uk.ac.horizon.artcodes.databinding.ExperienceEditInfoBinding;

public class ExperienceEditInfoFragment extends ExperienceEditFragment
{
	private ExperienceEditInfoBinding binding;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		binding = ExperienceEditInfoBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		binding.setExperience(getExperience());
	}
}