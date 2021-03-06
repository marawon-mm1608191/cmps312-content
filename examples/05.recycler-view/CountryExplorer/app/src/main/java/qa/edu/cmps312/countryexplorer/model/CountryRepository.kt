package json.country

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object CountryRepository {
    var countries = listOf<Country>()
    val count : Int get() = countries.size

    fun initCountries(context: Context) : List<Country> {
        if (countries.isEmpty()) {
            val countriesJson = context.assets
                                    .open("countries.json")
                                    .bufferedReader()
                                    .use { it.readText() }
            val json = Json { ignoreUnknownKeys = true }
            countries = json.decodeFromString(countriesJson)
        }
        return countries
    }

    fun getCountriesByContinent(continent: String) = countries.filter { it.continent.equals(continent, true) }
                                                              .sortedByDescending { it.population }

    fun getCountriesByRegion(region: String) = countries.filter { it.region.equals(region, true) }
                                                        .sortedBy { it.population }

    val continents : List<String> get() = countries.map { it.continent}.distinct().sorted()
    val countryNames : List<String> get() = countries.map { it.name}.sorted()

    fun getRegions(continent : String) =
            countries.filter { it.continent.equals(continent, true) }
                     .map { it.region }.distinct().sorted()
}