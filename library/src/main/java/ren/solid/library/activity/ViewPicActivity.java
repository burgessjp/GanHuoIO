package ren.solid.library.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ren.solid.library.R;
import ren.solid.library.fragment.ViewPicFragment;

/**
 * Created by _SOLID
 * Date:2016/4/22
 * Time:14:28
 * <p/>
 * view full picture
 */
public class ViewPicActivity extends ToolbarActivity {

    private static String TAG = "ViewPicActivity";
    public final static String IMG_URLS = "ImageUrls";
    private ArrayList<String> mUrlList;

    private ViewPicFragment mFragment;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mUrlList = getIntent().getExtras().getStringArrayList(IMG_URLS);

    }

    @Override
    protected void setUpView() {

        super.setUpView();

    }

    @Override
    protected String getToolbarTitle() {
        return "图片";
    }

    @Override
    protected Fragment setFragment() {
        mFragment = new ViewPicFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IMG_URLS, mUrlList);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewpic_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
//        RxPermissions.getInstance(getApplication())
//                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean granted) {
//                        if (granted) {
//                            if (id == R.id.action_save) {
//                                mFragment.downloadPicture(0);
//                            } else if (id == R.id.action_share) {
//                                mFragment.downloadPicture(1);
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "无读写权限", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        if (id == R.id.action_save) {
            mFragment.downloadPicture(0);
        } else if (id == R.id.action_share) {
            mFragment.downloadPicture(1);
        }
        return super.onOptionsItemSelected(item);
    }


}
