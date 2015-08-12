package uk.ac.horizon.artcodes.ui;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import uk.ac.horizon.artcodes.R;
import uk.ac.horizon.artcodes.model.Experience;
import uk.ac.horizon.artcodes.scanner.BR;

public class ExperienceEditor extends BaseObservable
{
	private final Context context;
	private final Experience experience;

	public ExperienceEditor(Context context, Experience experience)
	{
		this.context = context;
		this.experience = experience;
	}

	public SimpleTextWatcher getDescWatcher()
	{
		return new SimpleTextWatcher()
		{
			@Override
			public String getText()
			{
				return experience.getDescription();
			}

			@Override
			public void onTextChanged(String value)
			{
				if (!value.equals(experience.getDescription()))
				{
					experience.setDescription(value);
					experience.notifyPropertyChanged(BR.description);
				}
			}
		};
	}

	@Bindable
	public int getChecksum()
	{
		return experience.getChecksumModulo() - 1;
	}

	public int getChecksumMax()
	{
		return 11;
	}

	public CompoundButton.OnCheckedChangeListener getEmbeddedListener()
	{
		return new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				experience.setEmbeddedChecksum(isChecked);
				notifyPropertyChanged(uk.ac.horizon.artcodes.BR.checksumText);
			}
		};
	}

	public boolean getEmbeddedChecksum()
	{
		return experience.getEmbeddedChecksum();
	}

	public SeekBar.OnSeekBarChangeListener getChecksumListener()
	{
		return new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				experience.setChecksumModulo(progress + 1);
				notifyPropertyChanged(uk.ac.horizon.artcodes.BR.checksumText);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{

			}
		};
	}

	@Bindable
	public String getChecksumText()
	{
		if (experience.getChecksumModulo() <= 1)
		{
			return context.getString(R.string.checksumModulo_off);
		}
		else if (experience.getEmbeddedChecksum())
		{
			return context.getString(R.string.checksumModulo_embedded_value, experience.getChecksumModulo());
		}
		else
		{
			return context.getString(R.string.checksumModulo_value, experience.getChecksumModulo());
		}
	}

	public SimpleTextWatcher getNameWatcher()
	{
		return new SimpleTextWatcher()
		{
			@Override
			public String getText()
			{
				return experience.getName();
			}

			@Override
			public void onTextChanged(String value)
			{
				if (!value.equals(experience.getName()))
				{
					experience.setName(value);
					experience.notifyPropertyChanged(BR.name);
				}
			}
		};
	}

}
