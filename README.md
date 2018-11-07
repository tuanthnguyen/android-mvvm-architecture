## Android MVVM Architecture Sample

This project handles paging images and supports offline mode.

## How to build it

Thanks to Unsplash, this project uses their API to fetch beautiful images. 

Note: I added the token in this project. You can try it now instead of doing the steps below.

- Firstly, let's head to [Unsplash's developer page](https://unsplash.com/developers) to get your own API token.  
- Secondly, put it inside your `build.gradle` file as following:
```
buildConfigField "String", "UNSPLASH_TOKEN", "\"Client-ID *********\""
```

### Libraries
- AndroidX Support Library
- AndroidX Architecture Components(Room, ViewModels, LiveData)
- AndroidX Data Binding
- RxJava2
- Dagger2
- Retrofit2

### Contributing to Android MVVM Architecture
Just make pull request. You are in!

### License

This project is available under the MIT license. See the LICENSE file for more info.
