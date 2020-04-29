# GitHub-Popular

#### This Repository can be also used as a [Template](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/creating-a-repository-from-a-template)

Sample app to demonstrate offline feature using Kotlin, Repository pattern, Room Database, Retrofit, Dagger2, LiveData, Expresso, ViewModel and DataBinding.

The app fetches popular GitHub repositories data using **Retrofit** HTTP client and stores it in local **Room** Database. All bussiness logic i.e how to serve and from where to serve data are written in repository. Internally, network bound resource is being used to decide source of the data.

For dependency injection, **Dagger2** has been used. And functional testing is done by using **Espresso**.

It also shows basic information of the repository i.e. language (with color), number of stars, and number of forks.


<img src="demo_gif.gif?raw=true" width="350">
