package com.zcc.activity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Address;
import com.zcc.entity.Business;
import com.zcc.entity.Collect;
import com.zcc.entity.Order;
import com.zcc.entity.ShoppingCar;
import com.zcc.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements OnViewChangeListener {

    private static final String TAG = "GuideActivity";
    private ScrollLayout mScrollLayout;
    private ImageView[] imgs;
    private int count;
    private int currentItem;
    private RelativeLayout rl;
    private RelativeLayout mainRLayout;
    private LinearLayout pointLLayout;
    private String ip;
    private List<Business> mListBusiness;
    private List<User> mListUser;
    private List<ShoppingCar> mListShoppingCar;
    private List<Collect> mListCollect;
    private List<Order> mListOrder;
    private List<Address> mListAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        //GuideActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ip = getResources().getString(R.string.ip_address);
        init();
        initbusiness();
        initUser();
        initCollect();
        initAddress();
        initOrder();
        initShoppingcar();
    }

    private void initbusiness() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = ip + "Guide/LoadBusiness";
        mListBusiness = new ArrayList<Business>();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                //   Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_LONG).show();//解析Json数据
                //二、  JSON 数组的情况解析(    形如：{ "languag"：[{"key":"123","password","456"},
                //*		                             {"key":"ha","password":"haha"}]
                //*                     }     );
                JSONObject root = null;  //---新建JSONObject根对象 root;
                try {
                    root = new JSONObject(s.toString());
                    JSONArray arry = root.getJSONArray("datas");    // ---获得数组对象 arry;
                    //*(3)、            使用 for 循环 解析数据;
                    for (int i = 0; i < arry.length(); i++) {
                        Business business = new Business();
                        JSONObject lo = arry.getJSONObject(i);
                        String id = lo.getString("Id");
                        String name = lo.getString("name");
                        String type = lo.getString("type");
                        String imgurl = lo.getString("imgurl");
                        String price = lo.getString("price");
                        business.setId(Integer.parseInt(id));
                        business.setImgUrl(imgurl);
                        business.setName(name);
                        business.setPrice(price);
                        business.setType(type);
                        mListBusiness.add(business);
                    }
                    DBHelper.getInstance(getApplicationContext()).saveAll(mListBusiness);
                    Toast.makeText(getApplication(), "business添加成功!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "business添加成功!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    Toast.makeText(getApplication(), "business添加失败!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "business添加成功0000");
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }

    private void initUser() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = ip + "Guide/LoadUser";
        mListUser = new ArrayList<User>();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                JSONObject root = null;
                try {
                    root = new JSONObject(s.toString());
                    JSONArray arry = root.getJSONArray("datas");
                    for (int i = 0; i < arry.length(); i++) {
                        User user = new User();
                        JSONObject lo = arry.getJSONObject(i);
                        String id = lo.getString("Id");
                        String name = lo.getString("name");
                        String psw = lo.getString("password");
                        String imgurl = lo.getString("imgurl");
                        String phone = lo.getString("phone");
                        String sex = lo.getString("sex");
                        user.setId(Integer.parseInt(id));
                        user.setImgUrl(imgurl);
                        user.setName(name);
                        user.setPassword(psw);
                        user.setPhone(phone);
                        user.setSex(sex);
                        mListUser.add(user);
                    }
                    DBHelper.getInstance(getApplicationContext()).saveAll(mListUser);
                    Toast.makeText(getApplication(), "user添加成功!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "user添加成功!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    Toast.makeText(getApplication(), "user添加失败!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "user添加成功!0000");
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }

    private void initCollect() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = ip + "Guide/LoadCollect";
        mListCollect = new ArrayList<Collect>();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                JSONObject root = null;
                try {
                    root = new JSONObject(s.toString());
                    JSONArray arry = root.getJSONArray("datas");
                    for (int i = 0; i < arry.length(); i++) {
                        Collect collect = new Collect();
                        JSONObject lo = arry.getJSONObject(i);
                        String id = lo.getString("Id");
                        String userId = lo.getString("userId");
                        String businessId = lo.getString("businessId");
                        String collect_time = lo.getString("collect_time");
                        collect.setId(Integer.parseInt(id));
                        collect.setBusinessId(businessId);
                        collect.setUserId(userId);
                        collect.setCollect_time(collect_time);
                        mListCollect.add(collect);
                    }
                    DBHelper.getInstance(getApplicationContext()).saveAll(mListCollect);
                    Toast.makeText(getApplication(), "collect添加成功!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "collect添加成功!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    Toast.makeText(getApplication(), "collect添加失败!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "collect添加成功!00000");
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }

    private void initAddress() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = ip + "Guide/LoadAddress";
        mListAddress = new ArrayList<Address>();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                JSONObject root = null;
                try {
                    root = new JSONObject(s.toString());
                    JSONArray arry = root.getJSONArray("datas");
                    for (int i = 0; i < arry.length(); i++) {
                        Address address = new Address();
                        JSONObject lo = arry.getJSONObject(i);
                        String id = lo.getString("Id");
                        String userId = lo.getString("userId");
                        String adds = lo.getString("address");
                        address.setId(Integer.parseInt(id));
                        address.setUserId(userId);
                        address.setAddress(adds);
                        mListAddress.add(address);
                    }
                    DBHelper.getInstance(getApplicationContext()).saveAll(mListAddress);
                    Toast.makeText(getApplication(), "address添加成功!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "address添加成功!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    Toast.makeText(getApplication(), "address添加失败!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "address添加成功!00000");
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }

    private void initOrder() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = ip + "Guide/LoadOrder";
        mListOrder = new ArrayList<Order>();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                JSONObject root = null;
                try {
                    root = new JSONObject(s.toString());
                    JSONArray arry = root.getJSONArray("datas");
                    for (int i = 0; i < arry.length(); i++) {
                        //    {"datas":[{"Id":"1","businessId":"1","userId":"1","create_time":"2016-09-06",
                        //          "adddress":"\u56db\u5ddd\u7701\u6210\u90fd\u5e02\u90eb\u53bf\u7ea2\u5149\u9547\u897f\u534e\u5927\u5b66",
                        //         "count":"1","price":"199.00"}]}
                        Order order = new Order();
                        JSONObject lo = arry.getJSONObject(i);
                        String id = lo.getString("Id");
                        String userId = lo.getString("userId");
                        String create_time = lo.getString("create_time");
                        String adddress = lo.getString("adddress");
                        String businessId = lo.getString("businessId");
                        String price = lo.getString("price");
                        String count = lo.getString("count");
                        order.setId(Integer.parseInt(id));
                        order.setAddress(adddress);
                        order.setUserId(userId);
                        order.setBusinessId(businessId);
                        order.setCreateTime(create_time);
                        order.setPrice(price);
                        order.setCount(count);
                        mListOrder.add(order);
                    }
                    DBHelper.getInstance(getApplicationContext()).saveAll(mListOrder);
                    Toast.makeText(getApplication(), "Order添加成功!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "Order添加成功!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    Toast.makeText(getApplication(), "Order添加失败!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "Order添加成功!00000");
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }

    private void initShoppingcar() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = ip + "Guide/LoadOrder";
        mListShoppingCar = new ArrayList<ShoppingCar>();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                JSONObject root = null;
                try {
                    root = new JSONObject(s.toString());
                    JSONArray arry = root.getJSONArray("datas");
                    for (int i = 0; i < arry.length(); i++) {
//                        {"datas":[{"Id":"1","businessId":"1","userId":"1",
//                                "creat_time":"2016-09-06","count":"1","price":"199.00"}]}
                        ShoppingCar shoppingCar = new ShoppingCar();
                        JSONObject lo = arry.getJSONObject(i);
                        String id = lo.getString("Id");
                        String userId = lo.getString("userId");
                        String create_time = lo.getString("create_time");
                        String adddress = lo.getString("adddress");
                        String businessId = lo.getString("businessId");
                        String price = lo.getString("price");
                        String count = lo.getString("count");
                        shoppingCar.setId(Integer.parseInt(id));
                        shoppingCar.setUserId(userId);
                        shoppingCar.setBusinessId(businessId);
                        shoppingCar.setCreate_time(create_time);
                        shoppingCar.setPrice(price);
                        shoppingCar.setCount(count);
                        mListShoppingCar.add(shoppingCar);
                    }
                    DBHelper.getInstance(getApplicationContext()).saveAll(mListShoppingCar);
                    Toast.makeText(getApplication(), "ShoppingCar添加成功!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "ShoppingCar添加成功!");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    Toast.makeText(getApplication(), "ShoppingCar添加失败!", Toast.LENGTH_SHORT).show();
                    Log.e("database-----", "ShoppingCar添加成功00000!");
                    e.printStackTrace();
                }
                super.onSuccess(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayout);
        pointLLayout = (LinearLayout) findViewById(R.id.llayout);
        mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
        rl = (RelativeLayout) findViewById(R.id.guide_rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.setVisibility(View.GONE);
                pointLLayout.setVisibility(View.GONE);
                mainRLayout.setBackgroundResource(R.mipmap.guide3);
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                GuideActivity.this.startActivity(intent);
                GuideActivity.this.finish();
            }
        });
        count = mScrollLayout.getChildCount();
        imgs = new ImageView[count];
        for (int i = 0; i < count; i++) {
            imgs[i] = (ImageView) pointLLayout.getChildAt(i);
            imgs[i].setEnabled(true);
            imgs[i].setTag(i);
        }
        currentItem = 0;
        imgs[currentItem].setEnabled(false);
        mScrollLayout.SetOnViewChangeListener(this);
    }

    @Override
    public void OnViewChange(int position) {
        setcurrentPoint(position);
    }

    private void setcurrentPoint(int position) {
        if (position < 0 || position > count - 1 || currentItem == position) {
            return;
        }
        imgs[currentItem].setEnabled(true);
        imgs[position].setEnabled(false);
        currentItem = position;
    }


}
