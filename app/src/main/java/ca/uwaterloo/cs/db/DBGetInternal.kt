package ca.uwaterloo.cs.db

import ca.uwaterloo.cs.Listener
import ca.uwaterloo.cs.bemodels.SignUpFarmer
import ca.uwaterloo.cs.dbmodels.CompleteUserProfile
import ca.uwaterloo.cs.harvest.HarvestInformation
import ca.uwaterloo.cs.product.ProductInformation

class DBGetInternal {
    private val dbClient = DBClient()

    fun authenticate(userName:String, password: String, listener: Listener<Boolean>){
        class ListenerImpl() : Listener<SignUpFarmer>() {
            override fun activate(input: SignUpFarmer) {
                if (true){
                    listener.activate(true)
                }
                else{
                    listener.activate(false)
                }
            }
        }
        val authListener = ListenerImpl()
        dbClient.get(
            dataBasePathResolver(IdType.DFCPersonId) + userName,
            authListener
        )
    }

    fun authenticateFarmCode(farmCodeId: Id, beListener: Listener<String?>){
        dbClient.getIfExists(
            farmCodeId.getPath(),
            beListener
        )
    }


    fun getProductInformation(userId: String, beListener: Listener<List<ProductInformation>>){
        val productsInformation = mutableListOf<ProductInformation>()

        class ListenerImpl1() : Listener<ProductInformation>() {
            override fun activate(input: ProductInformation) {
                productsInformation.add(input)
            }
        }
        val listener1 = ListenerImpl1()

        class ListenerImpl2() : Listener<CompleteUserProfile>() {
            override fun activate(input: CompleteUserProfile) {
                val productIds = input.productIds
                for (productId in productIds){
                    val id = Id(productId, IdType.ProductId)
                    dbClient.get(id.getPath(), listener1)
                }
                Thread.sleep(2000)
                beListener.activate(productsInformation)
            }
        }

        val listener2 = ListenerImpl2()

        val id = Id(userId, IdType.CompleteUserProfileId)

        dbClient.get(id.getPath(), listener2)
    }

    fun getHarvestInformation(workerId: String, beListener: Listener<List<HarvestInformation>>){
        val harvestsInformation = mutableListOf<HarvestInformation>()

        class ListenerImpl1() : Listener<HarvestInformation>() {
            override fun activate(input: HarvestInformation) {
                harvestsInformation.add(input)
            }
        }
        val listener1 = ListenerImpl1()

        class ListenerImpl2() : Listener<CompleteUserProfile>() {
            override fun activate(input: CompleteUserProfile) {
                val harvestIds = input.harvestIds
                for (harvestId in harvestIds){
                    val id = Id(harvestId, IdType.HarvestId)
                    dbClient.get(id.getPath(), listener1)
                }
                Thread.sleep(2000)
                beListener.activate(harvestsInformation)
            }
        }

        val listener2 = ListenerImpl2()

        val id = Id(workerId, IdType.CompleteUserProfileId)

        dbClient.get(id.getPath(), listener2)
    }

    fun getHarvestInformationFromFarmer(farmerUserId: String, beListener: Listener<List<HarvestInformation>>){
        val harvestsInformation = mutableListOf<HarvestInformation>()

        class ListenerImpl1() : Listener<List<HarvestInformation>>() {
            override fun activate(input: List<HarvestInformation>) {
                harvestsInformation.addAll(input)
            }
        }
        val listener1 = ListenerImpl1()

        class ListenerImpl2() : Listener<List<HarvestInformation>>() {
            override fun activate(input: List<HarvestInformation>) {
                harvestsInformation.addAll(input)
            }
        }

        val listener2 = ListenerImpl2()
    }
}