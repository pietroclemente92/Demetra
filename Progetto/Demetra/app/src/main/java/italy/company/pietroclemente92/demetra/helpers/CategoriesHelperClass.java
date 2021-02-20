package italy.company.pietroclemente92.demetra.helpers;

import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {
    int image;
    String title, description;
    GradientDrawable drawable;

    public CategoriesHelperClass(GradientDrawable drawable, int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.drawable = drawable;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public GradientDrawable getGradient() {
        return drawable;
    }
}
