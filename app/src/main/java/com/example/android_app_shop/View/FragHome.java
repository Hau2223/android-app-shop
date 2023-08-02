package com.example.android_app_shop.View;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.PagerImageProductDetail;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FragHome extends Fragment {

    GridView ListProductHomePage;
    ProductShowInDetailAdapter adapterGrid;
    ProductHandlder productHandlder;
    ImageProductHandler imageProductHandler;
    ArrayList<Product> productArrayList = new ArrayList<>();

    BottomNavigationView bottomNavigationView;

    TextView SeeAll ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productHandlder = new ProductHandlder(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler = new ImageProductHandler(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler.initData();
        productHandlder.initData();
        productArrayList = productHandlder.loadProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_home, container, false);

        SeeAll = view.findViewById(R.id.TV_SeeAll);
        ListProductHomePage = (GridView) view.findViewById(R.id.gridProductHomePage);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);

        productHandlder = new ProductHandlder(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler = new ImageProductHandler(getContext(), "SMARTPHONE.db", null, 1);

        imageProductHandler.initData();
        productHandlder.initData();
        productArrayList = productHandlder.loadProduct();

        addEvents();
        showProduct();
        return view;
    }

    private void addEvents(){

        SeeAll.setPaintFlags(SeeAll.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_shop);
            }
        });

        ListProductHomePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) adapterGrid.getItem(i);
                int id = product.getID();
                FragProductDetails frag_productDetails = new FragProductDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                frag_productDetails.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameFragment);
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment);
                }
                fragmentTransaction.add(R.id.frameFragment, frag_productDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private ArrayList<Product> getAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product pr : productHandlder.loadProduct()) {
            int productID = pr.getID();
            String productName = pr.getNameProduct();
            String category = pr.getID_Category();
            String color = pr.getColor();
            int storage = pr.getStorage();
            float price = pr.getPrice();
            List<ImageProduct> imageURLs = imageProductHandler.getListImagesByProductId(productID);
            List<String> allImageURLs = new ArrayList<>();
            for (ImageProduct img : imageURLs) {
                allImageURLs.add(img.getURL());
            }
            Product product = new Product();
            product.setID(productID);
            product.setNameProduct(productName);
            product.setColor(color);
            product.setStorage(storage);
            product.setPrice(price);
            product.setImageURL(allImageURLs);
            product.setID_Category(category);
            productList.add(product);
        }
        return productList;
    }

    private void showProduct() {
        ArrayList<Product> productList = getAllProduct();
        adapterGrid = new ProductShowInDetailAdapter(getContext(), productList);
        ListProductHomePage.setAdapter(adapterGrid);
    }
}