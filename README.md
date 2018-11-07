## Android MVVM Architecture Sample

The examples to handle paging the photos(include offline mode feature)

## How to build it

Thanks to Unsplash, this project uses their API to fetch beautiful images. 

Note: Currently, I have added the token in this project. You can try it now.

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
```
Copyright (c) 2018 Dan Nguyen
   
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
   
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
   
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```